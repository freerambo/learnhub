package org.erian.examples.bootapi.service;

import static org.assertj.core.api.Assertions.*;

import java.util.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.erian.examples.bootapi.BootApiApplication;
import org.erian.examples.bootapi.domain.*;
import org.erian.examples.bootapi.repository.UserDao;
import org.erian.examples.bootapi.service.security.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BootApiApplication.class)
@DirtiesContext
public class ShiroServiceTest {

	@Autowired
	private UserService service;
	@Autowired
	private UserDao dao;
	Logger logger = LoggerFactory.getLogger(ShiroServiceTest.class);
	
	@Test
	public void find() {
		User objs = service.findByUsername("test1234");
		
		logger.warn(objs.toString()); 
		assertThat(objs).isNotNull();
	}
	
//	@Test
	public void save() {
		User obj = new User();
		obj.setUsername("test1234");
		obj.setPerms("admin:read,admin:write");
		obj.setRoles("admin,user");
		obj.setPlainPassword("123456");
//		obj.setPassword("39e92be87438c69070c73f53304d1a375ccf486e");
//		obj.setSalt("3858a20b07fe7db8");
		obj.setRegisterdate(new Date());
		service.registerUser(obj);
		assertThat(obj.id).isNotNull();
	}

	
	
}
