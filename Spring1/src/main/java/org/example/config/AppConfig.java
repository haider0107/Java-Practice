package org.example.config;

import org.example.Laptop;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public Laptop laptop(){
        return new Laptop();
    }
}
