package com.product.service.config;


import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class KafkaTopicConfig {

    @Bean
    @ConditionalOnProperty(name = "kafka.enabled", havingValue = "true")
    public NewTopic createOrdersTopic(){
        return new NewTopic("orders", 1, (short) 1);
    }

}
