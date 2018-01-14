package org.rambo.spring.boot.stateless.shiro.api;


import javax.servlet.http.HttpServletRequest;  

import org.apache.shiro.SecurityUtils;
import org.rambo.spring.boot.stateless.shiro.User;
import org.rambo.spring.boot.stateless.shiro.UserService;
import org.rambo.spring.boot.stateless.shiro.token.StatelessToken;
import org.rambo.spring.boot.stateless.shiro.token.TokenManager;
import org.slf4j.Logger;  
import org.slf4j.LoggerFactory;  
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.web.bind.annotation.RequestMapping;  
import org.springframework.web.bind.annotation.RequestMethod;  
import org.springframework.web.bind.annotation.RestController;  


@RestController  
@RequestMapping("/login")  
public class LoginController{  
    
    
  @Autowired  
  private TokenManager tokenManager;  
  
  @Autowired  
  private UserService userService;
    
  protected static final Logger logger = LoggerFactory.getLogger(LoginController.class);  
    
  @RequestMapping(value = "",method = RequestMethod.POST)  
  public StatelessToken login(String userCode, String password) {  
      logger.info("userCode:"+userCode);  
      User usr = userService.findbyCode(userCode);  
      if (usr == null) {  
          return new StatelessToken(userCode, "invalid user");  
      }  
      if(!password.equals(usr.getPwd())){  
          return new StatelessToken(userCode, "invalid user password");  
      }  
      //成功穿件token返回给客户端保存  
      StatelessToken createToken = tokenManager.createToken(userCode);  
      return createToken;  
  }  
    
  @RequestMapping(value = "/logout",method = RequestMethod.GET)  
  public String logout(HttpServletRequest request) { 
	  
      String authorization = request.getHeader("authorization");  
      StatelessToken token = tokenManager.getToken(authorization);  
      if(token!= null){  
          tokenManager.deleteToken(token.getUserCode());  
      }  
      SecurityUtils.getSubject().logout();  
      logger.info("用户登出");  
      return "logout success";  
  }  
    
    
}  
