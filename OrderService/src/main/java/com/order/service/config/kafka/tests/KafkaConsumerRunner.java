package com.order.service.config.kafka.tests;

import org.springframework.stereotype.Component;

@Component
public class KafkaConsumerRunner {

    private final CustomKafkaConsumer kafkaConsumer;

    public KafkaConsumerRunner(KafkaTopicRouter topicRouter) {
        EventProcessorRegistry processorRegistry = new EventProcessorRegistry();
        this.kafkaConsumer = new CustomKafkaConsumer(topicRouter, processorRegistry);
        startConsuming();
    }

    private void startConsuming() {
        kafkaConsumer.subscribeToAllTopics();
        kafkaConsumer.consumeMessages();
    }
}

