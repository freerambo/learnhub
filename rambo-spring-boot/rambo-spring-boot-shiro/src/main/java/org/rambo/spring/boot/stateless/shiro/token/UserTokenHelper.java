/*
 * Copyright: Energy Research Institute @ NTU
 * rambo-spring-boot-shiro
 * org.rambo.spring.boot.stateless.shiro -> UserTokenOperHelper.java
 * Created on 15 Dec 2017-5:21:49 pm
 */
package org.rambo.spring.boot.stateless.shiro.token;

import java.util.List;  

import net.sf.ehcache.Cache;  
import net.sf.ehcache.CacheManager;  
import net.sf.ehcache.Element;  
  
/** 
 * ehcache用户令牌帮助类 
 * @author yuanbo 
 * 
 */  
public class UserTokenHelper{  
      
    /** 
     * 对应ehcache.xml cache Name 
     */  
    private String userTokenCacheName ="userTokenCache";  
      
    /** 
     * ehcache缓存管理器 
     */  
    private CacheManager cacheManager;  
      
    public String getUserToken(String userCode){  
        Cache cache = getUserTokenCache();  
        if (cache == null) {  
            return null;  
        }else{  
            Element element = cache.get(userCode);  
            if(element == null){  
                return null;  
            }else{  
                Object objectValue = element.getObjectValue();  
                if(objectValue == null){  
                    return null;  
                }else{  
                    return (String)objectValue;  
                }  
            }  
        }  
    }  
      
    public Cache getUserTokenCache(){  
        Cache cache = cacheManager.getCache(userTokenCacheName);  
        return cache;  
    }  
      
    public void updateUserToken(String userCode,String token,long seconds){  
        Cache cache = getUserTokenCache();  
        Element e = new Element(userCode, token);  
        e.setTimeToLive(new Long(seconds).intValue());  
        cache.put(e);  
    }  
      
    public void deleteUserToken(String userCode){  
        Cache cache = getUserTokenCache();  
        cache.remove(userCode);  
    }  
  
    public String getUserTokenCacheName() {  
        return userTokenCacheName;  
    }  
  
    public void setUserTokenCacheName(String userTokenCacheName) {  
        this.userTokenCacheName = userTokenCacheName;  
    }  
  
    public CacheManager getCacheManager() {  
        return cacheManager;  
    }  
  
    public void setCacheManager(CacheManager cacheManager) {  
        this.cacheManager = cacheManager;  
    }  

}  