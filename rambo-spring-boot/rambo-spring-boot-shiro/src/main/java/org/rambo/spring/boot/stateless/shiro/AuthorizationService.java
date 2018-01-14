/*
 * Copyright: Energy Research Institute @ NTU
 * rambo-spring-boot-shiro
 * org.rambo.spring.boot.stateless.shiro -> AuthorizationService.java
 * Created on 15 Dec 2017-5:44:13 pm
 */
package org.rambo.spring.boot.stateless.shiro;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * function description：
 *
 * @author <a href="mailto:zhuyb@ntu.edu.sg">Rambo Zhu  </a>
 * @version v 1.0 
 * Create:  15 Dec 2017 5:44:13 pm
 */
@Service
public class AuthorizationService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());  

    public List<String> selectRoles(String principal) {  
        List<String> roles = new ArrayList<String>();  
        //从数据库获取权限并设置  
        logger.info("add roles");  
        if("admin".equals(principal)){  
            roles.add("admin");  
            roles.add("vistor");  
        }  
        return roles;  
          
    }  
}
