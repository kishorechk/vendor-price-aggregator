package com.integwise.aggregator.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
* Swagger doc configuration. This currently looks for API path matching /api/prices/**
* 
* @author Kishor Chukka
* 
*/
@Configuration
public class SwaggerConfig {     
	private static final Logger LOGGER =
		      LoggerFactory.getLogger(SwaggerConfig.class);
	@Bean
    public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)  
		          .select()                                  
		          .apis(RequestHandlerSelectors.any())              
		          .paths(PathSelectors.ant("/api/prices/**"))                        
		          .build();     
    }
}