/*
 * Copyright: Energy Research Institute @ NTU
 * rambo-spring-boot-project
 * org.rambo.spring.boot.project.api -> ShiroController.java
 * Created on 2 Mar 2017-4:10:15 pm
 */
package org.rambo.spring.boot.project.api;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.rambo.spring.boot.project.common.constants.MediaTypes;
import org.rambo.spring.boot.project.domain.*;
import org.rambo.spring.boot.project.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
/**
 * function description：
 *
 * @author <a href="mailto:zhuyb@ntu.edu.sg">Rambo Zhu  </a>
 * @version v 1.0 
 * Create:  2 Mar 2017 4:10:15 pm
 */

@RestController
public class IndexController {

    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private RoleDao roleDao;  
    @Autowired
    private PermissionDao permsDao;

    @RequestMapping(value={"/","index"},method=RequestMethod.GET, produces=MediaTypes.JSON_UTF_8)
    public String index(){
    	return "welcome to rambo springboot project";
    }
    
    @RequestMapping(value="data/roles",method=RequestMethod.GET, produces=MediaTypes.JSON_UTF_8)
    public List<Role> getAllRoles(){
    	return (List<Role>) roleDao.findAll();
    }
    @RequestMapping(value="data/perms",method=RequestMethod.GET, produces=MediaTypes.JSON_UTF_8)
    public List<Permission> getAllPerms(){
    	return (List<Permission>) permsDao.findAll();
    }
}