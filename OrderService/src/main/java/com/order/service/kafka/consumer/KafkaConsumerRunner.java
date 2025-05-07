package com.order.service.kafka.consumer;

import com.order.service.kafka.routers.EventProcessorRegistry;
import com.order.service.kafka.routers.EventTypeRegistry;
import com.order.service.kafka.routers.KafkaConsumerTopicRouter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumerRunner {

    private final CustomKafkaConsumer kafkaConsumer;

    @Autowired
    public KafkaConsumerRunner(EventTypeRegistry eventTypeRegistry, KafkaConsumerTopicRouter topicRouter, EventProcessorRegistry processorRegistry) {
        this.kafkaConsumer = new CustomKafkaConsumer(eventTypeRegistry, topicRouter, processorRegistry);

        startConsuming();
    }

    private void startConsuming() {
        kafkaConsumer.init();
    }
}

