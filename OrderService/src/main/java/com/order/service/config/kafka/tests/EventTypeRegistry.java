package com.order.service.config.kafka.tests;

import com.order.service.coreapi.events.product.ProductCreatedEvent;
import com.order.service.coreapi.events.product.ProductDeletedEvent;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class EventTypeRegistry {

    private final Map<String, Class<?>> eventTypeMap = new HashMap<>();

    public EventTypeRegistry() {
        eventTypeMap.put("ProductCreatedEvent", ProductCreatedEvent.class);
        eventTypeMap.put("ProductDeletedEvent", ProductDeletedEvent.class);
    }

    public Class<?> getEventClass(String eventType) {
        return eventTypeMap.get(eventType);
    }
}
