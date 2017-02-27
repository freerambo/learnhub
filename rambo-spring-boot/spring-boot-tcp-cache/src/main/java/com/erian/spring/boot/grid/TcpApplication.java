package com.erian.spring.boot.grid;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class TcpApplication {

	public static void main(String[] args) {
		SpringApplication.run(TcpApplication.class, args);
	}

}