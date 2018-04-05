/*
 * Copyright: Energy Research Institute @ NTU
 * rambo-spring-boot-project
 * org.rambo.spring.boot.project.repository -> IUserDao.java
 * Created on 2 Mar 2017-2:54:02 pm
 */
package org.erian.examples.bootapi.repository;


import org.erian.examples.bootapi.domain.User;
import org.erian.examples.bootapi.repository.BaseDao;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * function description：
 *
 * @author <a href="mailto:zhuyb@ntu.edu.sg">Rambo Zhu  </a>
 * @version v 1.0 
 * Create:  2 Mar 2017 2:54:02 pm
 */
@Repository("userDao")
@Transactional
public interface UserDao extends BaseDao<User, Integer> {

	User findByUsername(String username); 
}
