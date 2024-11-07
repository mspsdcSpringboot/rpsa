package com.rpsa.rpsa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RpsaSpringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(RpsaSpringbootApplication.class, args);
	}

}
