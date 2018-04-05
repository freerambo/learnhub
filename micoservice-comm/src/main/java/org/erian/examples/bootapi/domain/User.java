/*
 * Copyright: Energy Research Institute @ NTU
 * rambo-spring-boot-project
 * org.rambo.spring.boot.project.domain -> User.java
 * Created on 2 Mar 2017-2:37:58 pm
 */
package org.erian.examples.bootapi.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import org.erian.examples.bootapi.domain.IdEntity;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.ImmutableList;

/**
 * 
 * function description：
 *
 *
 *USER is a Reserve Word and needs to be escaped in query using square bracket [] while querying.

If you are using annotations, escape through single quotes. '', like below

https://stackoverflow.com/questions/38818302/incorrect-syntax-near-the-keyword-table-and-could-not-extract-resultset 

 * @author <a href="mailto:zhuyb@ntu.edu.sg">Rambo Zhu  </a>
 * @version v 1.0 
 * Create:  5 Oct 2017 12:47:25 pm
 */
@Entity
@Table(name="`user`") // user is reserved key for mssql, if we use it as table name so do in thus `user` 
public class User extends IdEntity implements Serializable {
	public static final long serialVersionUID = 1L;

    @NotEmpty(message = "Username can not be null")
    private String username;
    private String password; 
    
    @NotEmpty(message = "Password can not be null")
	@Transient
	private String plainPassword;
    
	private String salt;
	private String roles;
	private String perms;
	private Date registerdate;

	
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

	@JsonIgnore
    public String getPlainPassword() {
		return plainPassword;
	}

	public void setPlainPassword(String plainPassword) {
		this.plainPassword = plainPassword;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	public String getPerms() {
		return perms;
	}

	public void setPerms(String perms) {
		this.perms = perms;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public Date getRegisterdate() {
		return registerdate;
	}

	public void setRegisterdate(Date registerdate) {
		this.registerdate = registerdate;
	}

	// one user have many roles
	@Transient
	@JsonIgnore
	public List<String> getRoleList() {
		// return a list splited by ','
		return ImmutableList.copyOf(StringUtils.split(roles, ","));
	}
	
    // one user have many permissions
	@Transient
	@JsonIgnore
	public List<String> getPermList() {
		// return a list splited by ','
		return ImmutableList.copyOf(StringUtils.split(perms, ","));
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}