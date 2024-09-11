package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.example")
@ComponentScan(basePackages = {"com.example.controller", "com.example.service", "com.example.repository"})
@EnableJpaRepositories(basePackages = "com.example.repository")

public class ProductUserInventoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductUserInventoryApplication.class, args);
	}

}
