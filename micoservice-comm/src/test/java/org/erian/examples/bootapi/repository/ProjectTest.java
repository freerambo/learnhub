package org.erian.examples.bootapi.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.erian.examples.bootapi.BootApiApplication;
import org.erian.examples.bootapi.domain.*;
import org.erian.examples.bootapi.repository.*;



@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BootApiApplication.class)
@DirtiesContext
public class ProjectTest {

	@Autowired
	private ProjectDao testDao;

//	@Test
	public void find() {
		List<Project> objs = testDao.findAll();
		assertThat(objs).hasSize(1);
	}
	
	@Test
	public void save() {
		Project obj = new Project();
		obj.name = "testProject123";
		obj.description = "this is a test project";
		obj.logo = "here is the logo path";
		obj.createdBy = "testUser";
		obj.createdOn = new Date();
		obj.userId = 1000;
		testDao.save(obj);
		assertThat(obj.id).isNotNull();
	}

	
	
}
