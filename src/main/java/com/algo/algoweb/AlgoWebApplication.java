package com.algo.algoweb;

import java.text.ParseException;

import javax.annotation.PostConstruct;

import com.algo.algoweb.domain.User;
import com.algo.algoweb.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@ConfigurationPropertiesScan("com.algo.algoweb.properties")
public class AlgoWebApplication {
	public static void main(String[] args) {
		SpringApplication.run(AlgoWebApplication.class, args);
	}

}
