/*
 * Copyright: Energy Research Institute @ NTU
 * rambo-spring-boot-shiro
 * org.rambo.spring.boot.stateless.shiro.config -> StatelessAuthcFilter.java
 * Created on 15 Dec 2017-5:52:03 pm
 */
package org.erian.examples.bootapi.service.security;

import java.io.IOException;  

import javax.servlet.ServletRequest;  
import javax.servlet.ServletResponse;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;  
  
import org.apache.log4j.Logger;  
import org.apache.shiro.web.filter.AccessControlFilter;
import org.erian.examples.bootapi.service.security.token.StatelessToken;
import org.erian.examples.bootapi.service.security.token.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.util.StringUtils;  
//http://blog.csdn.net/v2sking/article/details/71941530
  
/** 
 * 无状态授权过滤器  
 * @author yuanbo 
 * 
 */  
public class StatelessAuthcFilter extends AccessControlFilter {  
      
      
    private final Logger logger = Logger.getLogger(StatelessAuthcFilter.class);  
      
//    @Autowired  
    private TokenManager tokenManager;  
      
    public TokenManager getTokenManager() {  
        return tokenManager;  
    }  
  
    public void setTokenManager(TokenManager tokenManager) {  
        this.tokenManager = tokenManager;  
    }  
  
    @Override  
    protected boolean isAccessAllowed(ServletRequest request,  
            ServletResponse response, Object mappedValue) throws Exception {  
        HttpServletRequest httpRequest = (HttpServletRequest) request;  
        logger.info("拦截到的url:" + httpRequest.getRequestURL().toString());  
        // 前段token授权信息放在请求头中传入  
        String authorization = httpRequest.getHeader("authorization");  
        if (StringUtils.isEmpty(authorization)) {  
            onLoginFail(response, "No authorization code");  
            return false;  
        }  
        // 获取无状态Token  
        StatelessToken accessToken = tokenManager.getToken(authorization);  
        if(StringUtils.isEmpty(accessToken)){
            onLoginFail(response, "Invalid authorization code");  
            return false;
        } 
        try {  
            // 委托给Realm进行登录  
            getSubject(request, response).login(accessToken);  
        } catch (Exception e) {  
            logger.error("auth error:" + e.getMessage(), e);  
            onLoginFail(response, "auth error:" + e.getMessage()); // 登录失败  
            return false;  
        }  
        // 通过isPermitted 才能调用doGetAuthorizationInfo方法获取权限信息  
        getSubject(request, response).isPermitted(httpRequest.getRequestURI());  
        return true;  
    }  
  
    @Override  
    protected boolean onAccessDenied(ServletRequest request,  
            ServletResponse response) throws Exception {  
        return false;  
    }  
      
     //登录失败时默认返回401状态码    
      private void onLoginFail(ServletResponse response,String errorMsg) throws IOException {    
        HttpServletResponse httpResponse = (HttpServletResponse) response;    
        httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);    
        httpResponse.setContentType("text/html");  
        httpResponse.setCharacterEncoding("utf-8");  
        httpResponse.getWriter().write(errorMsg);    
        httpResponse.getWriter().close();  
      }    
  
}  