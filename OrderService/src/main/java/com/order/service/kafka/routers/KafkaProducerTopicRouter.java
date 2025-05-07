package com.order.service.kafka.routers;



import com.order.service.coreapi.events.order.OrderCreatedEvent;
import com.order.service.coreapi.events.order.OrderStateUpdated;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class KafkaProducerTopicRouter {

    private final Map<Class<?>, String> eventTopicMap = new HashMap<>();

    public KafkaProducerTopicRouter() {

        eventTopicMap.put(OrderCreatedEvent.class, "orderCreated");
        eventTopicMap.put(OrderStateUpdated.class, "orderStateUpdated");

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
