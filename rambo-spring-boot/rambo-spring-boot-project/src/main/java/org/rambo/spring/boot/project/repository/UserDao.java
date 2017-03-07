/*
 * Copyright: Energy Research Institute @ NTU
 * rambo-spring-boot-project
 * org.rambo.spring.boot.project.repository -> IUserDao.java
 * Created on 2 Mar 2017-2:54:02 pm
 */
package org.rambo.spring.boot.project.repository;


import org.rambo.spring.boot.project.domain.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * function description：
 *
 * @author <a href="mailto:zhuyb@ntu.edu.sg">Rambo Zhu  </a>
 * @version v 1.0 
 * Create:  2 Mar 2017 2:54:02 pm
 */
@Repository
@Transactional
public interface UserDao extends BaseDao<User, String> {

	User findByUsername(String username); 
}
