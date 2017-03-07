/*
 * Copyright: Energy Research Institute @ NTU
 * rambo-spring-boot-project
 * org.rambo.spring.boot.project.domain -> User.java
 * Created on 2 Mar 2017-2:37:58 pm
 */
package org.rambo.spring.boot.project.domain;


import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * function description：
 *
 * @author <a href="mailto:zhuyb@ntu.edu.sg">Rambo Zhu  </a>
 * @version v 1.0 
 * Create:  2 Mar 2017 2:37:58 pm
 */


import org.hibernate.validator.constraints.NotEmpty;

public class User extends IdEntity{

    @NotEmpty(message = "用户名不能为空")
    private String username;
    @NotEmpty(message = "密码不能为空")
    private String password; 
        
    private List<Role> roleList;// 一个用户具有多个角色

    private List<Permission> permissionList;// 一个用户具有多个角色

    public User() {
        super();
    }

    public User(String username, String password) {
        super();
        this.username = username;
        this.password = password;
    }


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

	public List<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

    public Set<String> getRolesName() {
        List<Role> roles = getRoleList();
        Set<String> set = new HashSet<String>();
        for (Role role : roles) {
            set.add(role.rolename);
        }
        return set;
    }

	
    public List<Permission> getPermissionList() {
		return permissionList;
	}

	public void setPermissionList(List<Permission> permissionList) {
		this.permissionList = permissionList;
	}

	public Set<String> getPNames() {
        List<Permission> permissions = getPermissionList();
        Set<String> set = new HashSet<String>();
        for (Permission permission : permissions) {
            set.add(permission.permissionname);
        }
        return set;
    } 
}