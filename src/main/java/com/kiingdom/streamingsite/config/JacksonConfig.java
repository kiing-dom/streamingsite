package com.kiingdom.streamingsite.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.jakarta.Hibernate5JakartaModule;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {
    @Bean
    Hibernate5JakartaModule hibernate5Module() {
        return new Hibernate5JakartaModule();
    }

    @Bean
    ObjectMapper objectMapper(Hibernate5JakartaModule hibernate5Module) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(hibernate5Module);
        return objectMapper;
    }
}