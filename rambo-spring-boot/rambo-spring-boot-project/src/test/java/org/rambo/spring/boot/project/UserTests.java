package org.rambo.spring.boot.project;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.rambo.spring.boot.project.domain.Permission;
import org.rambo.spring.boot.project.domain.*;
import org.rambo.spring.boot.project.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.common.collect.Lists;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTests {

	@Autowired
	private UserDao dao;
	@Autowired
	private RoleDao rdao;
	@Autowired
	private PermissionDao permdao;
	@Test
	public void addPerms() {
		
		ArrayList<Permission> permissionList = Lists.newArrayList();
		permissionList.add(new Permission("del"));
		permissionList.add(new Permission("user:edit"));
		permissionList.add(new Permission("user:query"));
		permissionList.add(new Permission("view"));
		permissionList.add(new Permission("add"));
		permdao.save(permissionList);
		assertThat(permissionList.size() == 5);
	}
//	@Test
	public void addRole() {
		
		ArrayList<Role> roles = Lists.newArrayList();
		roles.add(new Role("admin"));
		roles.add(new Role("user"));
		roles.add(new Role("manager"));
		rdao.save(roles);
		assertThat(roles.size() == 3);
	}
//	@Test
	public void addUser() {
		dao.deleteAll();
		User user = new User("user","user");
		List<Role> roleList = new ArrayList();
		List<Permission> permissionList  = new ArrayList();
		roleList.add(new Role("user"));
		
		permissionList.add(new Permission("add"));
		permissionList.add(new Permission("query"));
		
		user.setRoleList(roleList);
		user.setPermissionList(permissionList);
		
		User saved = dao.save(user);
		
		
		assertThat(saved.getPassword().equals(user.getPassword()));
		assertThat(saved.getId()).isNotNull();
		
		
		user = new User("admin","admin");
		
		roleList.add(new Role("admin"));

		permissionList.add(new Permission("del"));
		permissionList.add(new Permission("user:edit"));
		permissionList.add(new Permission("user:query"));
		
	
		user.setRoleList(roleList);
		user.setPermissionList(permissionList);
		
		saved = dao.save(user);
		
		assertThat(saved.getPassword().equals(user.getPassword()));
		assertThat(saved.getId()).isNotNull();
	}

}