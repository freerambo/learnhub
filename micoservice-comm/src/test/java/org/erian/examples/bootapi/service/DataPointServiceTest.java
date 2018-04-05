package org.erian.examples.bootapi.service;

import static org.assertj.core.api.Assertions.*;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.erian.examples.bootapi.BootApiApplication;
import org.erian.examples.bootapi.domain.*;
import org.erian.examples.bootapi.repository.*;

import static org.assertj.core.api.Assertions.*;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BootApiApplication.class)
@DirtiesContext
public class DataPointServiceTest {

	@Autowired
	private  DataPointService service;

	@Test
	public void find() {
//		DataPoint demo = testDao.findOne(1001);
		String result = service.readDataPoint(1002);
		System.out.println("----------------------\n" + result);
//		assertThat(demo.id).isEqualTo(1001);
	}
	
}
