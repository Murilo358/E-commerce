package com.product.service.config;


import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic createOrdersTopic(){
        return new NewTopic("orders", 1, (short) 1);
    }

}
