package com.taller.catalogos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MsCatalogosApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsCatalogosApplication.class, args);
	}

}
