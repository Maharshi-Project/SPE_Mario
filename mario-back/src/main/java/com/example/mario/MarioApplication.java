package com.example.mario;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class MarioApplication {

	private static final Logger logger = LogManager.getLogger(MarioApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(MarioApplication.class, args);
		logger.info("Start of Execution");
		System.out.println("\nInside Main Class");
	}
}
