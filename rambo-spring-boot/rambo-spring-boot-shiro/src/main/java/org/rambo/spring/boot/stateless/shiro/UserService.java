/*
 * Copyright: Energy Research Institute @ NTU
 * rambo-spring-boot-shiro
 * org.rambo.spring.boot.stateless.shiro -> UserService.java
 * Created on 15 Dec 2017-5:31:19 pm
 */
package org.rambo.spring.boot.stateless.shiro;

import org.springframework.stereotype.Service;

/**
 * function description：
 *
 * @author <a href="mailto:zhuyb@ntu.edu.sg">Rambo Zhu  </a>
 * @version v 1.0 
 * Create:  15 Dec 2017 5:31:19 pm
 */
@Service
public class UserService {

	/**
	 * @function:
	 * @param userCode
	 * @return
	 * @author: Rambo Zhu     15 Dec 2017 5:32:34 pm
	 */
	public User findbyCode(String userCode) {
		// TODO Auto-generated method stub
		if("admin".equals(userCode))
			return new User("admin", "admin");
		return null;
	}

}
