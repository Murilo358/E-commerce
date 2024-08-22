package com.product.service.config.axon;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.product.service.config.jackson.JacksonIgnoreAvroPropertiesMixIn;
import org.axonframework.serialization.Serializer;
import org.axonframework.serialization.json.JacksonSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class AxonConfiguration {

    @Bean
    @Primary
    public Serializer defaultSerializer() {
        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.addMixIn(Object.class, JacksonIgnoreAvroPropertiesMixIn.class);

        return JacksonSerializer.builder().objectMapper(objectMapper).build();
    }


}
