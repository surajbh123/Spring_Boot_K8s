package com.ecommerce.orderservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.http.HttpClient;

@Configuration
public class OrderServiceConfig {

    @Bean
    public ObjectMapper getObjectMapper(){
        return new ObjectMapper();
    }
}
