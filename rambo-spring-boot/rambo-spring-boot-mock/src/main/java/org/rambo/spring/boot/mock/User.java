/*
 * Copyright: Energy Research Institute @ NTU
 * rambo-spring-boot-mock
 * org.rambo.spring.boot.mock -> User.java
 * Created on 12 Jun 2017-3:09:46 pm
 */
package org.rambo.spring.boot.mock;

/**
 * function description：
 *
 * @author <a href="mailto:zhuyb@ntu.edu.sg">Rambo Zhu  </a>
 * @version v 1.0 
 * Create:  12 Jun 2017 3:09:46 pm
 */
public class User { 

    private Long id; 
    private String name; 
    private Integer age;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	} 

    // 省略setter和getter 
    
    

}