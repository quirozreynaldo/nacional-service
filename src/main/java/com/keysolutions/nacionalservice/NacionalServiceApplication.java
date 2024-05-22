package com.keysolutions.nacionalservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class NacionalServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(NacionalServiceApplication.class, args);
	}

}
