package com.order.service.config.jackson;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObjectMapperConfig {
    @Bean
    public ObjectMapper objectMapper() {

        return new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .registerModule(new JacksonAvroModule())
                .addMixIn(Object.class, JacksonIgnoreAvroPropertiesMixIn.class)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
}