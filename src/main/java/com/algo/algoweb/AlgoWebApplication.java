package com.algo.algoweb;

import javax.annotation.PostConstruct;

import com.algo.algoweb.domain.Authority;
import com.algo.algoweb.domain.User;
import com.algo.algoweb.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@SpringBootApplication
public class AlgoWebApplication {

	@Autowired
	private UserRepository repository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostConstruct
	public void initUsers() throws ParseException {
		User user = new User("201668003", passwordEncoder.encode("201668003"), "최재영", new SimpleDateFormat("yyyy-MM-dd").parse("1997-04-11"), Authority.ROLE_ADMIN);
		repository.save(user);
	}
	public static void main(String[] args) {
		SpringApplication.run(AlgoWebApplication.class, args);
	}

}
