package com.example.Springboot_pratice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringbootPraticeApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootPraticeApplication.class, args);
	}

}
