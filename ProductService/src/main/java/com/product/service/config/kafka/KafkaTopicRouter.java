package com.product.service.config.kafka;

import com.product.service.coreapi.events.product.ProductCreatedEvent;
import com.product.service.coreapi.events.product.ProductDeletedEvent;
import com.product.service.coreapi.events.product.ProductInventoryUpdatedEvent;
import com.product.service.coreapi.events.product.ProductUpdatedEvent;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class KafkaTopicRouter {

    private final Map<Class<?>, String> eventTopicMap = new HashMap<>();

    public KafkaTopicRouter() {

        eventTopicMap.put(ProductCreatedEvent.class, "productCreated");
        eventTopicMap.put(ProductDeletedEvent.class, "productDeleted");
        eventTopicMap.put(ProductInventoryUpdatedEvent.class, "productInventoryUpdated");
        eventTopicMap.put(ProductUpdatedEvent.class, "productUpdated");

        //TODO DO FOR PROMOTIONS AND CATEGORIES
    }

    public String getTopicForEvent(Object event) {
        return eventTopicMap.getOrDefault(event.getClass(), "DLQ");
    }
}
