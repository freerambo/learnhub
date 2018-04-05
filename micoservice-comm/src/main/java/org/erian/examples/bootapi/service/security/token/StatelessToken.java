/*
 * Copyright: Energy Research Institute @ NTU
 * rambo-spring-boot-shiro
 * org.rambo.spring.boot.stateless.shiro -> Test.java
 * Created on 15 Dec 2017-5:11:20 pm
 */
package org.erian.examples.bootapi.service.security.token;


import javax.persistence.Transient;

import org.apache.shiro.authc.AuthenticationToken;  
  
public class StatelessToken implements AuthenticationToken {  

	private static final long serialVersionUID = -5794578485715602814L;

	private String principal;  
      
    private String credentials;  
      
    public StatelessToken(String principal, String credentials){  
        this.principal = principal;  
        this.credentials = credentials;
    }  
  
    @Transient
    @Override  
    public Object getPrincipal() {  
        return principal;  
    }  
    @Transient
    @Override  
    public Object getCredentials() {  
        return credentials;  
    }  
      
    public String getUserCode() {  
        return principal;  
    }  
  
    public String getToken() {  
        return credentials;  
    }  
  
    public boolean equals(Object obj) {
    	
    	if(obj == null || ! (obj instanceof StatelessToken)) return false;
    	
    	StatelessToken st = (StatelessToken) obj;
        return (this.credentials.equals(st.credentials) && this.principal.equals(st.principal));
    }
}  