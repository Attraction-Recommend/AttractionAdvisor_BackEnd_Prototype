package com.example.prototype_recommend_server.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

public class ModelConfig {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
