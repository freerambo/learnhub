/*
 * Copyright: Energy Research Institute @ NTU
 * rambo-spring-boot-project
 * org.rambo.spring.boot.project.domain -> Role.java
 * Created on 2 Mar 2017-2:39:39 pm
 */
package org.erian.examples.bootapi.domain;

import javax.persistence.Entity;

import org.erian.examples.bootapi.domain.IdEntity;

/**
 * function description：
 *
 * @author <a href="mailto:zhuyb@ntu.edu.sg">Rambo Zhu  </a>
 * @version v 1.0 
 * Create:  2 Mar 2017 2:39:39 pm
 */

public class Role{

	public String rolename;

	public Role(String rolename) {
		super();
		this.rolename = rolename;
	}
	
	

}