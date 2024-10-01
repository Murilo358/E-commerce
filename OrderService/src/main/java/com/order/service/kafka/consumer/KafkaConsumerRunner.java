package com.order.service.kafka.consumer;

import com.order.service.kafka.routers.EventProcessorRegistry;
import com.order.service.kafka.routers.EventTypeRegistry;
import com.order.service.kafka.routers.KafkaTopicRouter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class KafkaConsumerRunner {

    private final CustomKafkaConsumer kafkaConsumer;

    private final Map<String, Class<?>> eventTypeMap = new HashMap<>();

    @Autowired
    public KafkaConsumerRunner(EventTypeRegistry eventTypeRegistry, KafkaTopicRouter topicRouter, EventProcessorRegistry processorRegistry) {
        this.kafkaConsumer = new CustomKafkaConsumer(eventTypeRegistry, topicRouter, processorRegistry);

        startConsuming();
    }

    private void startConsuming() {
        kafkaConsumer.init();
    }
}

