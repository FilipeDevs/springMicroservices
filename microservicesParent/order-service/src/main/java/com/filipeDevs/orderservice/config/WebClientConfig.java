package com.filipeDevs.orderservice.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    // Add Load Balancing on this Eureka Client (since we will have multiple instances of the different microservices)

    // The logic of Load Balancer is part of the client itself, and it carries the list of
    // servers and determines to which server a particular request must be directed based
    // on some algorithm. (https://www.geeksforgeeks.org/spring-cloud-client-side-load-balancer/)
    @Bean
    @LoadBalanced
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }



}
