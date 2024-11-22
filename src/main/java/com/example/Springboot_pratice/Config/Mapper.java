package com.example.Springboot_pratice.Config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;

public class Mapper {

    @Bean
    public ObjectMapper objectMapper()
    {
        return new ObjectMapper();
    }
}
