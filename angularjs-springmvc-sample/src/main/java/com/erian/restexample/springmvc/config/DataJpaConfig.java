package com.erian.restexample.springmvc.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = {"com.erian.restexample.springmvc"})
public class DataJpaConfig {


}
