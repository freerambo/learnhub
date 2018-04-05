/*
 * Copyright: Energy Research Institute @ NTU
 * rambo-spring-boot-project
 * org.rambo.spring.boot.project.api -> ShiroController.java
 * Created on 2 Mar 2017-4:10:15 pm
 */
package org.erian.examples.bootapi.api.user;
import java.util.Map;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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

//@RequestMapping("/shiro")
//@RestController
public class ShiroController2 {

    private static final Logger logger = LoggerFactory.getLogger(ShiroController2.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value="/login",method=RequestMethod.GET)
    public String loginForm(Model model){
        model.addAttribute("user", new User());
        return "login";
    }
    
    @RequestMapping(value="/home",method=RequestMethod.GET)
    public String home(){
        return "user after login";
    }
    
//    @RequiresRoles("admin")
    @RequestMapping(value="/admin",method=RequestMethod.GET)
    public String admin(){
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
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
        //获取当前的Subject  
        Subject currentUser = SecurityUtils.getSubject();  
        try {  
            //在调用了login方法后,SecurityManager会收到AuthenticationToken,并将其发送给已配置的Realm执行必须的认证检查  
            //每个Realm都能在必要时对提交的AuthenticationTokens作出反应  
            //所以这一步在调用login(token)方法时,它会走到MyRealm.doGetAuthenticationInfo()方法中,具体验证方式详见此方法  
            logger.info("对用户[" + username + "]进行登录验证..验证开始");  
            currentUser.login(token);  
            logger.info("对用户[" + username + "]进行登录验证..验证通过");  
            
        }catch(UnknownAccountException uae){  
            logger.info("对用户[" + username + "]进行登录验证..验证未通过,未知账户");  
            redirectAttributes.addFlashAttribute("message", "未知账户");  
        }catch(IncorrectCredentialsException ice){  
            logger.info("对用户[" + username + "]进行登录验证..验证未通过,错误的凭证");  
            redirectAttributes.addFlashAttribute("message", "密码不正确");  
        }catch(LockedAccountException lae){  
            logger.info("对用户[" + username + "]进行登录验证..验证未通过,账户已锁定");  
            redirectAttributes.addFlashAttribute("message", "账户已锁定");  
        }catch(ExcessiveAttemptsException eae){  
            logger.info("对用户[" + username + "]进行登录验证..验证未通过,错误次数过多");  
            redirectAttributes.addFlashAttribute("message", "用户名或密码错误次数过多");  
        }catch(AuthenticationException ae){  
            //通过处理Shiro的运行时AuthenticationException就可以控制用户登录失败或密码错误时的情景  
            logger.info("对用户[" + username + "]进行登录验证..验证未通过,堆栈轨迹如下");  
            ae.printStackTrace();  
            redirectAttributes.addFlashAttribute("message", "用户名或密码不正确");  
        }  
        //验证是否登录成功  
        if(currentUser.isAuthenticated()){  
            logger.info("用户[" + username + "]登录认证通过(这里可以进行一些认证通过后的一些系统参数初始化操作)");  
//            return "redirect:/user";tokenManager.createToken(username)
            //成功穿件token返回给客户端保存  
            return tokenManager.createToken(username);  
        }else{  
            token.clear();  
//            return "redirect:/login";
        	return new StatelessToken(username, "invalid username or password");  
        }  
    }

    @RequestMapping(value="/logout",method=RequestMethod.GET)  
    public String logout(RedirectAttributes redirectAttributes ){ 
        //使用权限管理工具进行用户的退出，跳出登录，给出提示信息
        SecurityUtils.getSubject().logout();  
        redirectAttributes.addFlashAttribute("message", "您已安全退出");  
        return "redirect:/login";
    } 

    @RequestMapping(value="/forbidden", method=RequestMethod.GET)
    public String unauthorizedRole(){
        logger.info("------没有权限-------");
        return "403";
    }
//    @RequiresRoles("")
    @RequestMapping(value="/user", method=RequestMethod.GET)
    public String getUserList(Map<String, Object> model){
        return "user -> " + getCurrentUsername();
    }

   
    @RequestMapping(value="/user/edit/{userid}", method=RequestMethod.GET)
    public String getUserList(@PathVariable int userid){
        logger.info("------进入用户信息修改-------");
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