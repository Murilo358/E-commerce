package com.product.service.kafka.routers;

import com.product.service.coreapi.events.order.OrderCreatedEvent;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class KafkaConsumerTopicRouter {

    private final Map<Class<?>, String> eventTopicMap = new HashMap<>();

    public KafkaConsumerTopicRouter() {

        eventTopicMap.put(OrderCreatedEvent.class, "orderCreated");
    }

    public String getTopicForEvent(Object event) {
        return eventTopicMap.getOrDefault(event.getClass(), "DLQ");
    }

    public List<String> getAllTopics(){

        List<String> topics = new ArrayList<>();

        for (Map.Entry<Class<?>, String> classStringEntry : eventTopicMap.entrySet()) {
            topics.add(classStringEntry.getValue());

        }

        return topics;
    }
}
