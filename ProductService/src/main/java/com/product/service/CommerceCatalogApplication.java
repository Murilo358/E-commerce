package com.product.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CommerceCatalogApplication {

	public static void main(String[] args) {
		SpringApplication.run(CommerceCatalogApplication.class, args);
	}

}
