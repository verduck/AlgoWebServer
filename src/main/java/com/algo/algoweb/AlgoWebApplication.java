package com.algo.algoweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan("com.algo.algoweb.properties")
public class AlgoWebApplication {
	public static void main(String[] args) {
		SpringApplication.run(AlgoWebApplication.class, args);
	}

}
