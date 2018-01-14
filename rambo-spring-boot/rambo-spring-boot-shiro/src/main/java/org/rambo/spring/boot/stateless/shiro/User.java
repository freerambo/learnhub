/*
 * Copyright: Energy Research Institute @ NTU
 * rambo-spring-boot-shiro
 * org.rambo.spring.boot.stateless.shiro -> User.java
 * Created on 15 Dec 2017-5:17:43 pm
 */
package org.rambo.spring.boot.stateless.shiro;

/**
 * function description：
 *
 * @author <a href="mailto:zhuyb@ntu.edu.sg">Rambo Zhu  </a>
 * @version v 1.0 
 * Create:  15 Dec 2017 5:17:43 pm
 */
public class User {

	public String pwd;
	public String code;
	/**
	 * @param string
	 * @param string2
	 */
	public User(String code, String pwd) {
		// TODO Auto-generated constructor stub
		this.code = code;
		this.pwd = pwd;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	
	
}
