package org.erian.examples.bootapi.service;

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
import org.erian.examples.bootapi.service.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BootApiApplication.class)
@DirtiesContext
public class ProjectServiceTest {

	@Autowired
	private ProjectService service;
	@Test
	public void find() {
		List<Project> objs = service.findAll();
		assertThat(objs).hasSize(4);
	}
	
//	@Test
	public void save() {
		Project obj = new Project();
		obj.name = "testProject1";
		obj.description = "this is a test project";
		obj.logo = "here is the logo path";
		obj.createdBy = "testUser";
		obj.createdOn = new Date();
		obj.userId = 1000;
		service.saveProject(obj);
		assertThat(obj.id).isNotNull();
	}

	
	
}
