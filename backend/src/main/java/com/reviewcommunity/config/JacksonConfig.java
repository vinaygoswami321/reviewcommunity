package com.reviewcommunity.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
public class JacksonConfig {

    /*
    *  Configures the jackson object mapper with snake case naming strategy
    * */
    @Bean
    public ObjectMapper objectMapper(){
        return Jackson2ObjectMapperBuilder
                .json()
                .propertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE)
                .build();
    }
}
