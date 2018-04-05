package org.erian.examples.bootapi;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// SpringBoot identifer
@SpringBootApplication
public class BootApiApplication {

	/**
	 * start embedded Tomcat and initiate Spring context.
	 * 
	 */
	public static void main(String[] args) throws Exception {
		SpringApplication.run(BootApiApplication.class, args);
	}
	
}
