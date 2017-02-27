package com.erian.spring.boot.grid.config;

import com.google.common.base.Predicate;
import static com.google.common.base.Predicates.or;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import static springfox.documentation.builders.PathSelectors.regex;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 *
 * @author Rambo Zhu<asybzhu@gmail.com>
 * 
 *         visit at http://server:port/swagger-ui.html
 */
@Configuration
@EnableSwagger2
@ComponentScan("com.erian.spring.boot.grid.api")
// Loads the spring beans required by the framework
// @Profile(value = {"dev", "staging"})
public class SwaggerConfig {

	@Bean
	public Docket postsApi() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("public-api").apiInfo(apiInfo()).select()
				.paths(postPaths()).build();
	}

	private Predicate<String> postPaths() {
		return or(regex("/api/grid.*"), regex("/api/books.*"));
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Spring Boot API by Swagger UI")
				.description("Spring Boot Example API reference for developers")
				.termsOfServiceUrl("http://www.freerambo.com")
				.contact(new Contact("Rambo Zhu", "www.freerambo.com", "asybzhu@gmail.com"))
				.license("Apache License Version 2.0")
				.licenseUrl("https://github.com/springfox/springfox/blob/master/LICENSE").version("2.0").build();
	}

}
