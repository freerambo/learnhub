/*
 * Copyright: Energy Research Institute @ NTU
 * rambo-spring-boot-project
 * org.rambo.spring.boot.project.dubbo -> ProviderAPI.java
 * Created on 19 Oct 2017-5:16:20 pm
 */
package org.rambo.spring.boot.project.dubbo;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.ServiceConfig;

/**
 * function description：
 *
 * @author <a href="mailto:zhuyb@ntu.edu.sg">Rambo Zhu  </a>
 * @version v 1.0 
 * Create:  19 Oct 2017 5:16:20 pm
 */
public class ConsumerAPI {

	
	public static void main(String[] args) {

		// 当前应用配置
		ApplicationConfig application = new ApplicationConfig();
		application.setName("Demo");

		// 连接注册中心配置
		RegistryConfig registry = new RegistryConfig();
		registry.setAddress("10.20.130.230:9999");
		registry.setUsername("aaa");
		registry.setPassword("bbb");

		// 注意：ReferenceConfig为重对象，内部封装了与注册中心的连接，以及与服务提供方的连接

		// 引用远程服务
		ReferenceConfig<DemoService> reference = new ReferenceConfig<DemoService>(); // 此实例很重，封装了与注册中心的连接以及与提供者的连接，请自行缓存，否则可能造成内存和连接泄漏
		reference.setApplication(application);
		reference.setRegistry(registry); // 多个注册中心可以用setRegistries()
		reference.setInterface(DemoService.class);
		reference.setVersion("2.5.6");

		// 和本地bean一样使用DemoService
		DemoService demoService = reference.get(); // 注意：此代理对象内部封装了所有通讯细节，对象较重，请缓存复用
		
	 String hello = demoService.sayHello("world"); // execute remote invocation
     System.out.println("Out - " + hello); // show the result

		
	}
}


