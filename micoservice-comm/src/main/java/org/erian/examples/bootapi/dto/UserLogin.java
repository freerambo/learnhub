/*
 * Copyright: Energy Research Institute @ NTU
 * microservice-comm
 * org.erian.examples.bootapi.dto -> UserLogin.java
 * Created on 18 Dec 2017-5:11:10 pm
 */
package org.erian.examples.bootapi.dto;

import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * function description：
 *
 * @author <a href="mailto:zhuyb@ntu.edu.sg">Rambo Zhu  </a>
 * @version v 1.0 
 * Create:  18 Dec 2017 5:11:10 pm
 */
public class UserLogin {

	 	@NotEmpty(message = "Username can not be null")
	    private String username;
	    @NotEmpty(message = "Password can not be null")
	    @Transient
	    private String password; 
		

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

}
