/*
 * Copyright: Energy Research Institute @ NTU
 * rambo-spring-boot-shiro
 * org.rambo.spring.boot.stateless.shiro -> sad.java
 * Created on 15 Dec 2017-5:15:36 pm
 */
package org.rambo.spring.boot.stateless.shiro.token;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/** 
 * 默认token管理实现类 
 * @author rambo 
 * 
 */
@Service
public class TokenManager{  
   
	
	 /** 
     * 失效时间 单位秒 
     */  
    protected long expirateTime;  
      
    protected final Logger logger = LoggerFactory.getLogger(TokenManager.class);  
      
    protected String userTokenPrefix ="token_";  
      
    protected UserTokenHelper userTokenOperHelper;  
      
    //protected LoginFlagOperHelper loginFlagOperHelper;  
      
    public StatelessToken createToken(String userCode) {  
        StatelessToken tokenModel = null;  
        String token = userTokenOperHelper.getUserToken(userTokenPrefix+userCode);  
        if(StringUtils.isEmpty(token)){  
            token = createStringToken(userCode);  
        }  
        userTokenOperHelper.updateUserToken(userTokenPrefix+userCode, token, expirateTime);  
        tokenModel = new StatelessToken(userCode, token);  
        return tokenModel;  
    }  
        
    protected boolean checkMemoryToken(StatelessToken model) {  
        if(model == null){  
            return false;  
        }  
        String userCode = (String)model.getPrincipal();  
        String credentials = (String)model.getCredentials();  
        String token = userTokenOperHelper.getUserToken(userTokenPrefix+userCode);  
        if (token == null || !credentials.equals(token)) {  
            return false;  
        }  
        return true;  
    }  
      
    public StatelessToken getToken(String authentication){  
        if(StringUtils.isEmpty(authentication)){  
            return null;  
        }  
        String[] au = authentication.split("_");  
        if (au.length <=1) {  
            return null;  
        }  
        String userCode = au[0];  
        StringBuilder sb = new StringBuilder();  
        for (int i = 1; i < au.length; i++) {  
            sb.append(au[i]);  
            if(i<au.length-1){  
                sb.append("_");  
            }  
        }  
        return new StatelessToken(userCode, sb.toString());  
    }  
      
    public boolean check(String authentication) {  
        StatelessToken token = getToken(authentication);  
        if(token == null){  
            return false;  
        }  
        return checkMemoryToken(token);  
    }  
  
     
    public void deleteToken(String userCode) {  
        userTokenOperHelper.deleteUserToken(userTokenPrefix+userCode);  
    }  
  
    public long getExpirateTime() {  
        return expirateTime;  
    }  
  
    public void setExpirateTime(long expirateTime) {  
        this.expirateTime = expirateTime;  
    }  
  
    public UserTokenHelper getUserTokenHelper() {  
        return userTokenOperHelper;  
    }  
  
    public void setUserTokenHelper(UserTokenHelper userTokenOperHelper) {  
        this.userTokenOperHelper = userTokenOperHelper;  
    }  
      
    //public LoginFlagOperHelper getLoginFlagOperHelper() {  
    //  return loginFlagOperHelper;  
    //}  
  
    //public void setLoginFlagOperHelper(LoginFlagOperHelper loginFlagOperHelper) {  
    //  this.loginFlagOperHelper = loginFlagOperHelper;  
    //}  
      
    
    public String createStringToken(String userCode) {  
        //创建简易的32为uuid  
        return UUID.randomUUID().toString().replace("-", "");  
    }  
  
  
    public boolean checkToken(StatelessToken model) {  
        return checkMemoryToken(model);  
    }  
  
  
}  