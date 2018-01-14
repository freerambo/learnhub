/*
 * Copyright: Energy Research Institute @ NTU
 * rambo-spring-boot-shiro
 * org.rambo.spring.boot.stateless.shiro -> sad.java
 * Created on 15 Dec 2017-5:42:26 pm
 */
package org.rambo.spring.boot.stateless.shiro;

import org.apache.shiro.subject.Subject;  
import org.apache.shiro.subject.SubjectContext;  
import org.apache.shiro.web.mgt.DefaultWebSubjectFactory;  
  
/** 
 * 无状态主题工厂 
 * @author yuanbo 
 * 
 */  
public class StatelessDefaultSubjectFactory extends DefaultWebSubjectFactory {  
    @Override  
    public Subject createSubject(SubjectContext context) {  
        //不创建session    
        context.setSessionCreationEnabled(false);  
        return super.createSubject(context);  
    }  
}  