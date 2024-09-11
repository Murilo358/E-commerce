package com.order.service.kafka.routers;

import com.order.service.coreapi.events.product.ProductCreatedEvent;
import com.order.service.coreapi.events.product.ProductDeletedEvent;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.HashMap;
import java.util.Set;

@Component
public class KafkaTopicRouter {

    private final Map<Class<?>, String> eventTopicMap = new HashMap<>();
    private final Map<String, Class<?>> topicEventMap = new HashMap<>();

    public KafkaTopicRouter() {
        eventTopicMap.put(ProductCreatedEvent.class, "productCreated");
        eventTopicMap.put(ProductDeletedEvent.class, "productDeleted");

        for (Map.Entry<Class<?>, String> entry : eventTopicMap.entrySet()) {
            topicEventMap.put(entry.getValue(), entry.getKey());
        }
    }

    public String getTopicForEvent(Object event) {
        return eventTopicMap.getOrDefault(event.getClass(), "DLQ");
    }

    public Class<?> getEventTypeForTopic(String topic) {
        return topicEventMap.get(topic);
    }

    public Set<String> getAllTopics() {
        return topicEventMap.keySet();
    }
}
