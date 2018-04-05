/*
 * Copyright: Energy Research Institute @ NTU
 * rambo-spring-boot-shiro
 * org.rambo.spring.boot.stateless.shiro -> sad.java
 * Created on 15 Dec 2017-5:15:36 pm
 */
package org.erian.examples.bootapi.service.security.token;

import java.util.Map;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.apache.commons.codec.binary.Hex;
import org.assertj.core.util.Maps;
import org.erian.modules.utils.Digests;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/** 
 * 默认token管理实现类 
 * @author yuanbo 
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
    
	@Autowired
	private CacheManager cacheManager;
	
	public Cache tokenCache;
	
    //protected LoginFlagOperHelper loginFlagOperHelper;  
    
//    public static Map<String, StatelessToken> tokens = Maps.newConcurrentHashMap();
    
    @PostConstruct
    public void init(){
		tokenCache = this.cacheManager.getCache("TOKEN");
    }
    
    public StatelessToken createToken(String userCode) {  
    	
    	String token = Hex.encodeHexString(Digests.sha1(userTokenPrefix+userCode));
        StatelessToken tokenModel = tokenCache.get(token, StatelessToken.class);

    	/*if(tokens.containsKey(token)){
    		return tokens.get(token);
    	}*/
        if(null != tokenModel){
        	tokenCache.evict(tokenModel);
//        	tokenCache.put(token,tokenModel);
    	}else{
            tokenModel = new StatelessToken(userCode, token); 
    	}
        tokenCache.put(token,tokenModel);
        return tokenModel;  
    }  
        
    protected boolean checkMemoryToken(StatelessToken model) {  
        if(model == null){  
            return false;  
        }  
//        StatelessToken token = tokens.get(model.getToken());
        StatelessToken token = tokenCache.get(model.getToken(), StatelessToken.class);
        if (!model.equals(token)) {  
            return false;  
        }  
        return true;  
    }  
      
    public StatelessToken getToken(String authentication){  
        if(StringUtils.isEmpty(authentication)){  
            return null;  
        }  
        return tokenCache.get(authentication, StatelessToken.class); 
    }  
      
    public boolean check(String authentication) {  
        StatelessToken token = getToken(authentication);  
        if(token == null){  
            return false;  
        }  
        return checkMemoryToken(token);  
    }  
  
     
    public void deleteToken(String userCode) {  
    	
    	String token = Hex.encodeHexString(Digests.sha1(userTokenPrefix+userCode));
//        tokens.remove(token);
        tokenCache.evict(token);
    }  

    /*    public String createStringToken(String userCode) {  
        //创建简易的32为uuid  
        return UUID.randomUUID().toString().replace("-", "");  
    }  */
  
  
    public boolean checkToken(StatelessToken model) {  
        return checkMemoryToken(model);  
    }

	public CacheManager getCacheManager() {
		return cacheManager;
	}

	public void setCacheManager(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}  
    
    
    
    
}  