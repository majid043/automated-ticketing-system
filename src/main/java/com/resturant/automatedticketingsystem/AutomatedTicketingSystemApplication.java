package com.resturant.automatedticketingsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition (
		info = @Info (
				title = "Automated Ticketing System",
				version = "v1"
				)
		)
@SpringBootApplication
@EnableScheduling
@EnableAsync
public class AutomatedTicketingSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(AutomatedTicketingSystemApplication.class, args);
	}

}
