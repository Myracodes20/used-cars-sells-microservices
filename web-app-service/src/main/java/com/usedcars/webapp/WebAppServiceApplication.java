package com.usedcars.webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class WebAppServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebAppServiceApplication.class, args);
    }

}