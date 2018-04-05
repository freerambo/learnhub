/*
 * Copyright: Energy Research Institute @ NTU
 * rambo-spring-boot-project
 * org.rambo.spring.boot.project.config -> ShiroConfig.java
 * Created on 2 Mar 2017-1:43:40 pm
 */
package org.erian.examples.bootapi.config;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.credential.PasswordMatcher;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.web.filter.authc.AnonymousFilter;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.filter.authc.UserFilter;
import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;
import org.apache.shiro.web.filter.authz.RolesAuthorizationFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.DefaultWebSubjectFactory;
import org.erian.examples.bootapi.service.security.DBShiroRealm;
import org.erian.examples.bootapi.service.security.StatelessAuthcFilter;
import org.erian.examples.bootapi.service.security.token.StatelessToken;
import org.erian.examples.bootapi.service.security.token.TokenManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.CacheManager;
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

	
	static final String HASH_ALGORITHM = "SHA-1";
	static final Integer HASH_INTERATIONS = 1024;
	
    private static final Logger logger = LoggerFactory.getLogger(ShiroConfig.class);
    
    @Bean
    public EhCacheManager getEhCacheManager() {  
        EhCacheManager em = new EhCacheManager();
//        Cache<String, StatelessToken> cache = em.getCache("tokens");
        em.setCacheManagerConfigFile("classpath:cache/ehcache-shiro.xml");  
        return em;  
    }
    
    
/*    @Bean(name = "credentialsMatcher")
    public HashedCredentialsMatcher credentialsMatcher() {
        final HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName(HASH_ALGORITHM);
        credentialsMatcher.setHashIterations(HASH_INTERATIONS);
        return credentialsMatcher;
    }*/
    
    @Bean(name = "realm")
	@DependsOn(value={"lifecycleBeanPostProcessor"})
	public DBShiroRealm realm() {
		DBShiroRealm realm = new DBShiroRealm();
		realm.setCacheManager(getEhCacheManager());
//		realm.setCredentialsMatcher(credentialsMatcher());
		return realm;
	}

	@Bean(name = "shiroFilter")
	public ShiroFilterFactoryBean shiroFilter(TokenManager tokenManager) {
		ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
		shiroFilter.setLoginUrl("/shiro/login");
		shiroFilter.setSuccessUrl("/shiro/index");
		shiroFilter.setUnauthorizedUrl("/shiro/forbidden");
        //Noted LinkedHashMap is ordered  
		Map<String, String> filterChainDefinitionMapping = new LinkedHashMap<String, String>();

		filterChainDefinitionMapping.put("/", "anon");
		filterChainDefinitionMapping.put("/swagger-ui.html", "anon");
		
		filterChainDefinitionMapping.put("/shiro/home", "statelessAuthc,roles[user]");
		filterChainDefinitionMapping.put("/shiro/admin", "statelessAuthc,roles[admin]");
		filterChainDefinitionMapping.put("/shiro/user", "statelessAuthc,roles[user]");
		filterChainDefinitionMapping.put("/shiro/user/edit/**", "statelessAuthc,perms[user:edit]");
		filterChainDefinitionMapping.put("/shiro/login", "anon");
		filterChainDefinitionMapping.put("/shiro/forbidden", "anon");

		filterChainDefinitionMapping.put("/user/user", "statelessAuthc");
        filterChainDefinitionMapping.put("/shiro/user/edit/**", "statelessAuthc,perms[user:edit]");
//        filterChainDefinitionMapping.put("/api/**", "statelessAuthc");//statelessAuthc token filter
        filterChainDefinitionMapping.put("/**", "anon");//anon no any operations 

        
		shiroFilter.setFilterChainDefinitionMap(filterChainDefinitionMapping);
		shiroFilter.setSecurityManager(securityManager());

		Map<String, Filter> filters = new HashMap<String, Filter>();
		filters.put("anon", new AnonymousFilter());
		filters.put("authc", new FormAuthenticationFilter());
		filters.put("logout", new LogoutFilter());
		filters.put("roles", new RolesAuthorizationFilter());
		filters.put("user", new UserFilter());
//		filters.put("perms", new PermissionsAuthorizationFilter();
		filters.put("statelessAuthc", statelessAuthcFilter(tokenManager));
		shiroFilter.setFilters(filters);
		return shiroFilter;
	}

	@Bean(name = "securityManager")
	@DependsOn(value={"realm"})
	public SecurityManager securityManager() {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(realm());
		/**
		 * set ehcache as cachemanager
		 */
		securityManager.setCacheManager(getEhCacheManager());
		 //禁用sessionStorage  
        DefaultSubjectDAO de = (DefaultSubjectDAO) securityManager.getSubjectDAO();  
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator =(DefaultSessionStorageEvaluator)de.getSessionStorageEvaluator();  
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false); 
        
        //create stateless Subject, forbidden the session  
        StatelessDefaultSubjectFactory statelessDefaultSubjectFactory = new StatelessDefaultSubjectFactory();  
        securityManager.setSubjectFactory(statelessDefaultSubjectFactory);  
        
		return securityManager;
	}
	
	@Bean
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}
	
	 /** 
     * SessionManager, forbidden session 
     * @return 
     */  
    @Bean  
    public DefaultSessionManager defaultSessionManager(){  
         logger.info("ShiroConfig.getDefaultSessionManager()");  
         DefaultSessionManager manager = new DefaultSessionManager();  
         manager.setSessionValidationSchedulerEnabled(false);  
         return manager;  
    }  

    
    /** 
     *  
     * @Function: ShiroConfig::statelessAuthcFilter 
     * @Description:  
     * Do not set the StatelessAuthcFilter with annotation @Bean, otherwise shiro will be not able 
     * to manage the filters' life circle
     * 
     * 无状态授权过滤器 注意不能声明为bean 否则shiro无法管理该filter生命周期，<br> 
     *                 该过滤器会执行其他过滤器拦截过的路径 
     */  
    public StatelessAuthcFilter statelessAuthcFilter(TokenManager tokenManager){  
        logger.info("ShiroConfig.statelessAuthcFilter()");  
        StatelessAuthcFilter statelessAuthcFilter = new StatelessAuthcFilter();  
        statelessAuthcFilter.setTokenManager(tokenManager);  
        return statelessAuthcFilter;  
   }  
    
// below two methods support the shiro annotation http://stackoverflow.com/questions/7743749/shiro-authorization-permission-check-using-annotation-not-working
/*	@Bean
	@ConditionalOnMissingBean
	public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor(SecurityManager securityManager) {
	    // This is to enable Shiro's security annotations
	    AuthorizationAttributeSourceAdvisor sourceAdvisor = new AuthorizationAttributeSourceAdvisor();
	    sourceAdvisor.setSecurityManager(securityManager);
	    return sourceAdvisor;
	}

	@ConditionalOnMissingBean
	@Bean(name = "defaultAdvisorAutoProxyCreator")
	@DependsOn("lifecycleBeanPostProcessor")
	public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
	    DefaultAdvisorAutoProxyCreator proxyCreator = new DefaultAdvisorAutoProxyCreator();
	    proxyCreator.setProxyTargetClass(true);
	    return proxyCreator;
	}*/
	
	/**
	 *  Stateless SubjectFactory
	 */
	class StatelessDefaultSubjectFactory extends DefaultWebSubjectFactory {  
	    @Override  
	    public Subject createSubject(SubjectContext context) {  
	        //do not create session    
	        context.setSessionCreationEnabled(false);  
	        return super.createSubject(context);  
	    }  
	} 
	
}
 
