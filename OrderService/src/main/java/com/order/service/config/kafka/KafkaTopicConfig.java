package com.order.service.config.kafka;


import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class KafkaTopicConfig {

    @Bean
    @ConditionalOnProperty(name = "kafka.enabled", havingValue = "true")
    public NewTopic createProductCreatedTopic() {
        return new NewTopic("productCreated", 1, (short) 1);
    }

    @Bean
    @ConditionalOnProperty(name = "kafka.enabled", havingValue = "true")
    public NewTopic createProductUpdatedTopic() {
        return new NewTopic("productUpdated", 1, (short) 1);
    }

    @Bean
    @ConditionalOnProperty(name = "kafka.enabled", havingValue = "true")
    public NewTopic createProductInventoryUpdatedTopic() {
        return new NewTopic("productInventoryUpdated", 1, (short) 1);
    }

    @Bean
    @ConditionalOnProperty(name = "kafka.enabled", havingValue = "true")
    public NewTopic createProductDeletedTopic() {
        return new NewTopic("productDeleted", 1, (short) 1);
    }

    @Bean
    @ConditionalOnProperty(name = "kafka.enabled", havingValue = "true")
    public NewTopic createCategoryCreatedTopic() {
        return new NewTopic("categoryCreated", 1, (short) 1);
    }

    @Bean
    @ConditionalOnProperty(name = "kafka.enabled", havingValue = "true")
    public NewTopic createOrderCreatedTopic() {
        return new NewTopic("orderCreated", 1, (short) 1);
    }

    @Bean
    @ConditionalOnProperty(name = "kafka.enabled", havingValue = "true")
    public NewTopic createCategoryUpdatedTopic() {
        return new NewTopic("categoryUpdated", 1, (short) 1);
    }

    @Bean
    @ConditionalOnProperty(name = "kafka.enabled", havingValue = "true")
    public NewTopic createCategoryDeletedTopic() {
        return new NewTopic("categoryDeleted", 1, (short) 1);
    }

    @Bean
    @ConditionalOnProperty(name = "kafka.enabled", havingValue = "true")
    public NewTopic createPromotionCreatedTopic() {
        return new NewTopic("promotionCreated", 1, (short) 1);
    }

    @Bean
    @ConditionalOnProperty(name = "kafka.enabled", havingValue = "true")
    public NewTopic createPromotionDeletedTopic() {
        return new NewTopic("promotionDeleted", 1, (short) 1);
    }

}
