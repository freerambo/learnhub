package com.erian.spring.boot.grid;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;


import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class RandomPortExampleTests {

	private static Logger log = LoggerFactory.getLogger(RandomPortExampleTests.class);

@Value("${device.command.setStatus}")
String command;
	

	@Test
	public void exampleTest() {
		
		
		command = String.format(command, "off");
		System.out.print(command);
		System.out.print(command);
		log.warn(command);
		assertNotNull(command);
			 String result = "";
//				result = SocketConnection.testCache("192.168.127.110", 2101, "asdkgkasdgksak");
//			 System.out.println("outp:rel?;:sour:freq?;:volt:ac?;:volt:lim:ac?;:POW:prot?;:curr?;\n"); 
	}

}