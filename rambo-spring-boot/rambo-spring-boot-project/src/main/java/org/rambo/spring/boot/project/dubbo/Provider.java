/*
 * Copyright: Energy Research Institute @ NTU
 * rambo-spring-boot-project
 * org.rambo.spring.boot.project.dubbo -> Provider.java
 * Created on 19 Oct 2017-4:58:22 pm
 */
package org.rambo.spring.boot.project.dubbo;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Provider {
    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                new String[] {"classpath:*dubbo/dubbo-demo-provider.xml"});
        context.start();
        System.in.read(); // press any key to exit
    }
}