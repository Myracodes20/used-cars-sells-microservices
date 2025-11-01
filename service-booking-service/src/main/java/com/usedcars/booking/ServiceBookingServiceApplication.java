package com.usedcars.booking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ServiceBookingServiceApplication {
	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(ServiceBookingServiceApplication.class, args);
	}
}

