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
public class DeviceTest {

	@Autowired
	private DeviceDao testDao;

//	@Test
	public void find() {
		List<Device> objs = testDao.findAll();
		assertThat(objs).hasSize(3);
	}
	
	@Test
	public void save() {
		Device obj = new Device();
		obj.name = "bic2";
		obj.description = "this is a bic2";
		obj.protocol = "CANBUS";
		obj.path = "ws://192.168.127.13:8888/bic/con";
		obj.address = null;
		obj.createdBy = "yuanbo";
		obj.createdOn = new Date();
		obj.type = "Converter";
		obj.projectId = 1001;
		obj.freq = "SEC01";
		testDao.save(obj);
		System.out.println(obj.id);
		assertThat(obj.id).isNotNull();
	}

	
}
