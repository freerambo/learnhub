package org.rambo.spring.boot.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

/**
 * Hello world!
 *
 */

@EnableCaching
@SpringBootApplication
public class Application 
{
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
