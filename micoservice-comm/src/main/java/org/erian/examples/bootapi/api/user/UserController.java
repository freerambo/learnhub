/*
 * Copyright: Energy Research Institute @ NTU
 * rambo-spring-boot-project
 * org.rambo.spring.boot.project.api -> ShiroController.java
 * Created on 2 Mar 2017-4:10:15 pm
 */
package org.erian.examples.bootapi.api.user;
import java.util.List;
import javax.validation.constraints.NotNull;

import org.erian.modules.constants.MediaTypes;
import org.erian.examples.bootapi.domain.*;
import org.erian.examples.bootapi.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
/**
 * function description：
 *
 * @author <a href="mailto:zhuyb@ntu.edu.sg">Rambo Zhu  </a>
 * @version v 1.0 
 * Create:  2 Mar 2017 4:10:15 pm
 */

@RequestMapping("/user")
@RestController
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserDao userDao;

    @RequestMapping(method=RequestMethod.GET, produces=MediaTypes.JSON_UTF_8)
    public List<User> getAllUsers(){
    	return (List<User>) userDao.findAll();
    }
    
    @RequestMapping(value="/{username}",method=RequestMethod.GET)
    public User home(@NotNull @PathVariable("username") String username){
    	return userDao.findByUsername(username);
    }
   
}