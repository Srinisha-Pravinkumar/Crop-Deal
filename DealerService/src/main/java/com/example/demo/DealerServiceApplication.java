package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication

@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.example.demo.feign")  
public class DealerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DealerServiceApplication.class, args);
		System.out.println("####################   DealerService Running...   ######################");
	}

}
