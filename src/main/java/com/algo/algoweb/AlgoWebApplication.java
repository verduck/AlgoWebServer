package com.algo.algoweb;

import javax.annotation.PostConstruct;

import com.algo.algoweb.domain.User;
import com.algo.algoweb.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class AlgoWebApplication {

	@Autowired
	private UserRepository repository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostConstruct
	public void initUsers() {
		User user = new User("201668003", passwordEncoder.encode("201668003"));
		repository.save(user);
	}
	public static void main(String[] args) {
		SpringApplication.run(AlgoWebApplication.class, args);
	}

}
