/*
 * Copyright: Energy Research Institute @ NTU
 * rambo-spring-boot-project
 * org.rambo.spring.boot.project.api -> ShiroController.java
 * Created on 2 Mar 2017-4:10:15 pm
 */
package org.erian.examples.bootapi.api.user;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.erian.examples.bootapi.domain.*;
import org.erian.examples.bootapi.dto.UserLogin;
import org.erian.examples.bootapi.repository.*;
import org.erian.examples.bootapi.service.security.UserService;
import org.erian.examples.bootapi.service.security.token.StatelessToken;
import org.erian.examples.bootapi.service.security.token.TokenManager;
import org.erian.modules.utils.Digests;
import org.erian.modules.utils.Encodes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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

@RequestMapping("/shiro")
@RestController
public class ShiroController {

    private static final Logger logger = LoggerFactory.getLogger(ShiroController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value="/login",method=RequestMethod.GET)
    public String loginForm(Model model){
        model.addAttribute("user", new User());
        return "login";
    }
    
    @RequestMapping(value="/home",method=RequestMethod.GET)
    public String home(@RequestHeader(value="authorization") String authorization){
        return "user after login";
    }
    
//    @RequiresRoles("admin")
    @RequestMapping(value="/admin",method=RequestMethod.GET)
    public String admin(@RequestHeader(value="authorization", required=false) String authorization){
        return "admin after login";
    }

    @RequestMapping(value="/login",method=RequestMethod.POST)
    public StatelessToken login(@RequestBody @Valid UserLogin user,BindingResult bindingResult,RedirectAttributes redirectAttributes){
        
    	 StatelessToken createToken = null;
    	 
    	if(bindingResult.hasErrors()){
        	for(ObjectError error: bindingResult.getAllErrors()){
        		logger.warn(error.getObjectName() + " : " + error.getDefaultMessage());
        	}
        	return new StatelessToken(user.getUsername(), "invalid input");  
        }
        String username = user.getUsername();
        User u = userService.findByUsername(username);
        if(u != null){
        	 if(userService.validPassword(u, user.getPassword())){
            	 return tokenManager.createToken(username);  
            }
         	return new StatelessToken(username, "invalid credentials");  
        }
    	return new StatelessToken(username, "invalid username");  
    }


    @RequestMapping(value="/forbidden", method=RequestMethod.GET)
    public String unauthorizedRole(){
        logger.info("------No Permission-------");
        return "403";
    }
//    @RequiresRoles("")
    @RequestMapping(value="/user", method=RequestMethod.GET)
    public String getUserList(@RequestHeader(value="authorization") String authorization){
        return "user -> " + getCurrentUsername();
    }
    @RequestMapping(value="/logout",method=RequestMethod.GET)  
    public String logout(@RequestHeader(value="authorization", required=false) String authorization, RedirectAttributes redirectAttributes){
       StatelessToken token = tokenManager.getToken(authorization);  
       if(token!= null){  
           tokenManager.deleteToken(token.getUserCode());  
       }  
        //When user logout give a message
        SecurityUtils.getSubject().logout();  
        redirectAttributes.addFlashAttribute("message", "Successfully logout");  
        return "redirect:/login";
    } 
   
    @RequestMapping(value="/user/edit/{userid}", method=RequestMethod.GET)
    public String getUserList(@PathVariable int userid, @RequestHeader(value="authorization") String authorization){
        logger.info("------inter into user modification-------");
        return "user_edit";
    }
    
	/**
	 * get the current user Id from shiro.
	 */
	private String getCurrentUsername() {
		String username = (String)SecurityUtils.getSubject().getPrincipal();
		return username;
	}
	
	@Autowired  
    private TokenManager tokenManager;  
}