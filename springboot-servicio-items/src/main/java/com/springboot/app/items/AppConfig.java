package com.springboot.app.items;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    @Bean("clienteRest")
    // Con @LoadBalanced se habilita el balanceo de carga en Ribbon para restTemplate
    @LoadBalanced
    public RestTemplate registrarRestTemplate() {
        return new RestTemplate();
    }
}
