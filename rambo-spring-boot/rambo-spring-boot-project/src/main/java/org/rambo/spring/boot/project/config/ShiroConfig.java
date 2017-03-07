/*
 * Copyright: Energy Research Institute @ NTU
 * rambo-spring-boot-project
 * org.rambo.spring.boot.project.config -> ShiroConfig.java
 * Created on 2 Mar 2017-1:43:40 pm
 */
package org.rambo.spring.boot.project.config;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.text.PropertiesRealm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.AnonymousFilter;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.filter.authc.UserFilter;
import org.apache.shiro.web.filter.authz.RolesAuthorizationFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.rambo.spring.boot.project.service.security.DBShiroRealm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.filter.CharacterEncodingFilter;


/**
 * function description：
 * http://blog.csdn.net/catoop/article/details/50520958
 * @author <a href="mailto:zhuyb@ntu.edu.sg">Rambo Zhu  </a>
 * @version v 1.0 
 * Create:  2 Mar 2017 1:43:40 pm
 */
@Configuration
public class ShiroConfig {

    private static final Logger logger = LoggerFactory.getLogger(ShiroConfig.class);

    @Autowired
    CacheManager cacheManager;
    
    
     
    @Bean
    public EhCacheManager getEhCacheManager() {  
        EhCacheManager em = new EhCacheManager();  
        em.setCacheManagerConfigFile("classpath:cache/ehcache-shiro.xml");  
        return em;  
    }
    
	@Bean(name = "realm")
	@DependsOn("lifecycleBeanPostProcessor")
	public DBShiroRealm realm() {
		DBShiroRealm realm = new DBShiroRealm();
		realm.setCacheManager(getEhCacheManager());
		return realm;
	}
	
	@Bean(name = "characterEncodingFilter")
	public FilterRegistrationBean characterEncodingFilter() {
		FilterRegistrationBean bean = new FilterRegistrationBean();
		bean.addInitParameter("encoding", "UTF-8");
		bean.addInitParameter("forceEncoding", "true");
		bean.setFilter(new CharacterEncodingFilter());
		bean.addUrlPatterns("/user/*");   // here we can customized our url filter 
		return bean;
	}
	

	@Bean(name = "shiroFilter")
	public ShiroFilterFactoryBean shiroFilter() {
		ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
		shiroFilter.setLoginUrl("/user/login");
		shiroFilter.setSuccessUrl("/user/index");
		shiroFilter.setUnauthorizedUrl("/user/forbidden");
		Map<String, String> filterChainDefinitionMapping = new HashMap<String, String>();

		filterChainDefinitionMapping.put("/", "anon");
		filterChainDefinitionMapping.put("/user/home", "authc,roles[user]");
		filterChainDefinitionMapping.put("/user/admin", "authc,roles[admin]");
		filterChainDefinitionMapping.put("/user/user/edit/**", "authc,perms[user:edit]");
		filterChainDefinitionMapping.put("/login", "anon");
		 // authc：该过滤器下的页面必须验证后才能访问，它是Shiro内置的一个拦截器org.apache.shiro.web.filter.authc.FormAuthenticationFilter
		filterChainDefinitionMapping.put("/user/user", "authc");// 这里为了测试，只限制/user，实际开发中请修改为具体拦截的请求规则
        // anon：它对应的过滤器里面是空的,什么都没做
        logger.info("##################从数据库读取权限规则，加载到shiroFilter中##################");
        filterChainDefinitionMapping.put("/user/user/edit/**", "authc,perms[user:edit]");// 这里为了测试，固定写死的值，也可以从数据库或其他配置中读取
        filterChainDefinitionMapping.put("/**", "anon");//anon 可以理解为不拦截
		
		shiroFilter.setFilterChainDefinitionMap(filterChainDefinitionMapping);
		shiroFilter.setSecurityManager(securityManager());

		Map<String, Filter> filters = new HashMap<String, Filter>();
		filters.put("anon", new AnonymousFilter());
		filters.put("authc", new FormAuthenticationFilter());
		filters.put("logout", new LogoutFilter());
		filters.put("roles", new RolesAuthorizationFilter());
		filters.put("user", new UserFilter());
		shiroFilter.setFilters(filters);
		return shiroFilter;
	}

	@Bean(name = "securityManager")
	public SecurityManager securityManager() {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(realm());
		/**
		 * set ehcache as cachemanager
		 */
		securityManager.setCacheManager(getEhCacheManager());
		return securityManager;
	}
	

	@Bean
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}
	
}
