package com.order.service.kafka.consumer;

import com.order.service.kafka.routers.EventProcessorRegistry;
import com.order.service.kafka.routers.KafkaTopicRouter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class KafkaConsumerRunner {

    private final CustomKafkaConsumer kafkaConsumer;

    private final Map<String, Class<?>> eventTypeMap = new HashMap<>();

    public KafkaConsumerRunner(KafkaTopicRouter topicRouter) {
        EventProcessorRegistry processorRegistry = new EventProcessorRegistry();
        this.kafkaConsumer = new CustomKafkaConsumer(topicRouter, processorRegistry);
        startConsuming();
    }

    private void startConsuming() {
        kafkaConsumer.init();
    }
}

