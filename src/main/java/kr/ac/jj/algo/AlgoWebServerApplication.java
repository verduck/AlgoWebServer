package kr.ac.jj.algo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan("kr.ac.jj.algo.properties")
public class AlgoWebServerApplication {
	public static void main(String[] args) {
		SpringApplication.run(AlgoWebServerApplication.class, args);
	}

}
