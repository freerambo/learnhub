/*
 * Copyright: Energy Research Institute @ NTU
 * rambo-spring-boot-project
 * org.rambo.spring.boot.project.dubbo -> ProviderAPI.java
 * Created on 19 Oct 2017-5:16:20 pm
 */
package org.rambo.spring.boot.project.dubbo;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.ServiceConfig;

/**
 * function description：
 *
 * @author <a href="mailto:zhuyb@ntu.edu.sg">Rambo Zhu  </a>
 * @version v 1.0 
 * Create:  19 Oct 2017 5:16:20 pm
 */
public class ProviderAPI {

	
	public static void main(String[] args) {

		// 服务实现
		DemoService DemoService = new DemoServiceImpl();

		// 当前应用配置
		ApplicationConfig application = new ApplicationConfig();
		application.setName("Demo");

		// 连接注册中心配置
		RegistryConfig registry = new RegistryConfig();
		registry.setAddress("10.20.130.230:9999");
		registry.setUsername("aaa");
		registry.setPassword("bbb");

		// 服务提供者协议配置
		ProtocolConfig protocol = new ProtocolConfig();
		protocol.setName("dubbo");
		protocol.setPort(12345);
		protocol.setThreads(200);

		// 注意：ServiceConfig为重对象，内部封装了与注册中心的连接，以及开启服务端口

		// 服务提供者暴露服务配置
		ServiceConfig<DemoService> service = new ServiceConfig<DemoService>(); // 此实例很重，封装了与注册中心的连接，请自行缓存，否则可能造成内存和连接泄漏
		service.setApplication(application);
		service.setRegistry(registry); // 多个注册中心可以用setRegistries()
		service.setProtocol(protocol); // 多个协议可以用setProtocols()
		service.setInterface(DemoService.class);
		service.setRef(DemoService);
		service.setVersion("2.5.6");

		// 暴露及注册服务
		service.export();
		
	}
}


