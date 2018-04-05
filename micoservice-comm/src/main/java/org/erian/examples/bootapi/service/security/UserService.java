/*
 * Copyright: Energy Research Institute @ NTU
 * microservice-comm
 * org.erian.spring.boot.shiro.service.security -> UserService.java
 * Created on 4 Oct 2017-3:51:49 pm
 */
package org.erian.examples.bootapi.service.security;


import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.erian.examples.bootapi.domain.User;
import org.erian.examples.bootapi.repository.UserDao;
import org.erian.modules.utils.Digests;
import org.erian.modules.utils.Encodes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 用户管理类.
 * 
 * @author calvin
 */
// Spring Service Bean的标识.
@Service
@Transactional
public class UserService {

	public static final String HASH_ALGORITHM = "SHA-1";
	public static final int HASH_INTERATIONS = 1024;
	private static final int SALT_SIZE = 8;

	private static Logger logger = LoggerFactory.getLogger(UserService.class);

	private UserDao userDao;

	public List<User> getAllUser() {
		return (List<User>) userDao.findAll();
	}

	public User getUser(Integer id) {
		return userDao.findOne(id);
	}

	public User findByUsername(String loginName) {
		return userDao.findByUsername(loginName);
	}
	public void registerUser(User user) {
		entryptPassword(user);
		user.setRegisterdate(new Date());
		logger.info(user.toString());
		userDao.save(user);
	}

	public void updateUser(User user) {
		if (StringUtils.isNotBlank(user.getPlainPassword())) {
			entryptPassword(user);
		}
		userDao.save(user);
	}

	public void deleteUser(Integer id) {
		
		userDao.delete(id);

	}



	/**
	 * 设定安全的密码，生成随机的salt并经过1024次 sha-1 hash
	 */
	private void entryptPassword(User user) {
		byte[] salt = Digests.generateSalt(SALT_SIZE);
		user.setSalt(Encodes.encodeHex(salt));
		byte[] hashPassword = Digests.sha1(user.getPlainPassword().getBytes(), salt, HASH_INTERATIONS);
		user.setPassword(Encodes.encodeHex(hashPassword));
	}
	
	public boolean validPassword(User user, String password) {
		byte[] salt = Encodes.decodeHex(user.getSalt());
		String hashPassword = Encodes.encodeHex(Digests.sha1(password.getBytes(), salt, HASH_INTERATIONS));
		return user.getPassword().equals(hashPassword);
	}

	@Autowired
	@Qualifier("userDao")
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	public void checkUserExists(String userCode) throws AuthenticationException {
		User principal = this.findByUsername(userCode);
		if(principal == null){
			throw new UnknownAccountException("userCode "+userCode+" wasn't in the system");
		}
	}

}
