/*
 * Copyright: Energy Research Institute @ NTU
 * rambo-spring-boot-project
 * org.rambo.spring.boot.project.domain -> sda.java
 * Created on 2 Mar 2017-2:40:06 pm
 */
package org.rambo.spring.boot.project.domain;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * function description：
 *
 * @author <a href="mailto:zhuyb@ntu.edu.sg">Rambo Zhu  </a>
 * @version v 1.0 
 * Create:  2 Mar 2017 2:40:06 pm
 */


@Document(collection="permission")
public class Permission extends IdEntity{

    public String permissionname;

	public Permission(String permissionname) {
		super();
		this.permissionname = permissionname;
	}
    
}