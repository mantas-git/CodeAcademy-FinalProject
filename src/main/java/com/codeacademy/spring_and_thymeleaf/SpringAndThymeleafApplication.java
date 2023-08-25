package com.codeacademy.spring_and_thymeleaf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringAndThymeleafApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringAndThymeleafApplication.class, args);

		PositionSimulator positionSimulator = new PositionSimulator();
		positionSimulator.runSimulator();
	}

}
