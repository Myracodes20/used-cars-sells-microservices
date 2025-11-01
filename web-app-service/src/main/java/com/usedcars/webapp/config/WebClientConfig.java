package com.usedcars.webapp.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    @LoadBalanced
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }
    
    @Bean
    public WebClient inventoryWebClient(WebClient.Builder builder) {
        return builder.baseUrl("http://inventory-service").build();
    }
    
    @Bean
    public WebClient userWebClient(WebClient.Builder builder) {
        return builder.baseUrl("http://user-service").build();
    }
    
    @Bean
    public WebClient bookingWebClient(WebClient.Builder builder) {
        return builder.baseUrl("http://service-booking-service").build();
    }
    
}