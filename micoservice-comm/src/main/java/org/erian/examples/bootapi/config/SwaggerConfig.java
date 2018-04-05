package org.erian.examples.bootapi.config;

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
 */
@Configuration
@EnableSwagger2
@ComponentScan("org.erian.examples.bootapi.api")
// Loads the spring beans required by the framework
//@Profile(value = {"dev", "staging"})
public class SwaggerConfig {

	  @Bean
	    public Docket postsApi() {
	        return new Docket(DocumentationType.SWAGGER_2)
	                .groupName("public-api")
	                .apiInfo(apiInfo())
	                .select()
	                .paths(postPaths())
	                .build();
	    }

	    private Predicate<String> postPaths() {
	        return or(
	                regex("/api/projects.*"),
	                regex("/api/devices.*"),
	                regex("/api/dataPoints.*"),
	                regex("/api/dp.*"),
	                regex("/api/ips.*"),
	                regex("/api/tcps.*"),
	                regex("/api/rtus.*"),
	                regex("/api/dpv.*"),
	                regex("/schedule.*"), // scheduler 
	                regex("/api.*"),
	                regex("/shiro.*")
	        );
	    }

	    private ApiInfo apiInfo() {
	        return new ApiInfoBuilder()
	                .title("SMES WP2 Configuration API")
	                .description("Spring-Boot Based Restful API reference for developers")
	                .termsOfServiceUrl("http://erian.ntu.edu.sg/Pages/Home.aspx")
	                .contact(new Contact("Rambo Zhu", "http://erian.ntu.edu.sg", "asybzhu@gmail.com"))
	                .license("Apache License Version 2.0")
	                .licenseUrl("https://github.com/springfox/springfox/blob/master/LICENSE")
	                .version("2.0")
	                .build();
	    }

}
