/*
 * Copyright: Energy Research Institute @ NTU
 * rambo-spring-boot-shiro
 * org.rambo.spring.boot.stateless.shiro -> Test.java
 * Created on 15 Dec 2017-5:11:20 pm
 */
package org.rambo.spring.boot.stateless.shiro.token;


import org.apache.shiro.authc.AuthenticationToken;  
  
public class StatelessToken implements AuthenticationToken {  

	private static final long serialVersionUID = -5794578485715602814L;

	private String userCode;  
      
    private String token;  
      
    public StatelessToken(String userCode, String token){  
        this.userCode = userCode;  
        this.token = token;  
    }  
  
    @Override  
    public Object getPrincipal() {  
        return userCode;  
    }  
  
    @Override  
    public Object getCredentials() {  
        return token;  
    }  
      
    public String getUserCode() {  
        return userCode;  
    }  
  
    public String getToken() {  
        return token;  
    }  
  
  
}  