package com.erian.spring.boot.grid;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.erian.spring.boot.grid.repository.GridSimulatorRepository;

@EnableCaching
@EnableScheduling
@EnableMongoRepositories(basePackageClasses = {GridSimulatorRepository.class})
@SpringBootApplication
public class TcpApplication {

	public static void main(String[] args) {
		SpringApplication.run(TcpApplication.class, args);
	}

}