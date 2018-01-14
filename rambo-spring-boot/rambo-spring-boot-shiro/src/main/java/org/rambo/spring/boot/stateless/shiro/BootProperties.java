/*
 * Copyright: Energy Research Institute @ NTU
 * rambo-spring-boot-shiro
 * org.rambo.spring.boot.stateless.shiro.config -> BootProperties.java
 * Created on 15 Dec 2017-5:49:31 pm
 */
package org.rambo.spring.boot.stateless.shiro;


import org.springframework.boot.context.properties.ConfigurationProperties;  
import org.springframework.stereotype.Component;  

@Component  
@ConfigurationProperties(prefix = "shiro.token")   
public class BootProperties {  
    
  private String key;  
     
  private long expirateTime;  

  public String getKey() {  
      return key;  
  }  

  public void setKey(String key) {  
      this.key = key;  
  }  

  public long getExpirateTime() {  
      return expirateTime;  
  }  

  public void setExpirateTime(long expirateTime) {  
      this.expirateTime = expirateTime;  
  }  
     
     

}  