package com.integwise;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class Application {

	public Application(Environment env) {
    }
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}