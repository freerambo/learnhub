package org.rambo.spring.boot.weixin;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
 
public class DemoListener implements ServletContextListener{
 
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("==>DemoListener start");
		
	}
 
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("==>DemoListener Destroyed");
	}
 
}