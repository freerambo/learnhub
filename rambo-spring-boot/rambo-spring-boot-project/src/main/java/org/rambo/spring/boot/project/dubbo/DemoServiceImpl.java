/*
 * Copyright: Energy Research Institute @ NTU
 * rambo-spring-boot-project
 * org.rambo.spring.boot.project.dubbo -> DemoserviceImpl.java
 * Created on 19 Oct 2017-4:52:07 pm
 */
package org.rambo.spring.boot.project.dubbo;

/**
 * function description：
 *
 * @author <a href="mailto:zhuyb@ntu.edu.sg">Rambo Zhu  </a>
 * @version v 1.0 
 * Create:  19 Oct 2017 4:52:07 pm
 */
public class DemoServiceImpl implements DemoService {

	/* (non-Javadoc)
	 * @see org.rambo.spring.boot.project.dubbo.DemoService#sayHello(java.lang.String)
	 */
	@Override
	public String sayHello(String name) {
		// TODO Auto-generated method stub
        return "Hello " + name;
	}

}
