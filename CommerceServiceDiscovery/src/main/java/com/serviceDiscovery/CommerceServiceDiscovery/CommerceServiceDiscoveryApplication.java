package com.serviceDiscovery.CommerceServiceDiscovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class CommerceServiceDiscoveryApplication {

	public static void main(String[] args) {
		SpringApplication.run(CommerceServiceDiscoveryApplication.class, args);
	}

}
