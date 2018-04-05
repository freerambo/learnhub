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
public class ProtocolTest {

	@Autowired
	private ModbusTcpDao testDao;
	
	@Autowired
	private ModbusRtuDao rtuDao;
	
	@Autowired
	private EthernetIpDao ipDao;

	@Test
	public void findModbuTCP() {
		ModbusTCP obj = testDao.findByDeviceId(1000);
		assertThat(obj.id).isEqualTo(1000);
	}
	
/*	@Test
	public void findModbuTCP() {
		List<ModbusTCP> objs = testDao.findByDeviceId(1000);
		assertThat(objs).hasSize(3);
	}*/
	
//	@Test
	public void saveModbusTCP() {
		ModbusTCP obj = new ModbusTCP();
		obj.description = "test ModbusTCP";
		obj.deviceId = 1000;
		obj.description = "This is Modbus TCP";
		obj.ip = "192.21.74.179";
		obj.port = 512;
		obj.createdBy = "testUser";
		obj.createdOn = new Date();
		testDao.save(obj);
		assertThat(obj.id).isNotNull();
	}

	
//	@Test
	public void saveModbusRTU() {
		ModbusRTU obj = new ModbusRTU();
		obj.description = "test ModbusTCP";
		obj.deviceId = 1001;
		obj.description = "This is Modbus TCP";
		obj.address = "COM1";
		obj.baudrate = 9600;
		obj.databit = 8;
		obj.stopbit = 1;
		obj.parity = "even";
		obj.createdBy = "testUser";
		obj.encoding = "rtu";
		obj.createdOn = new Date();
		rtuDao.save(obj);
		assertThat(obj.id).isNotNull();
	}

//	@Test
	public void saveEthernetIP() {
		EthernetIP obj = new EthernetIP();
		obj.description = "test ModbusTCP";
		obj.deviceId = 1000;
		obj.description = "This is Modbus TCP";
		obj.ip = "192.21.74.179";
		obj.port = 512;
		obj.createdBy = "testUser";
		obj.createdOn = new Date();
		ipDao.save(obj);
		assertThat(obj.id).isNotNull();
	}
	
}
