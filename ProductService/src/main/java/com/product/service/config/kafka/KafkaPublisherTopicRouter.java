package com.product.service.config.kafka;

import com.product.service.coreapi.events.category.CategoryCreatedEvent;
import com.product.service.coreapi.events.category.CategoryDeletedEvent;
import com.product.service.coreapi.events.category.CategoryUpdatedEvent;
import com.product.service.coreapi.events.product.ProductCreatedEvent;
import com.product.service.coreapi.events.product.ProductDeletedEvent;
import com.product.service.coreapi.events.product.ProductInventoryUpdatedEvent;
import com.product.service.coreapi.events.product.ProductUpdatedEvent;
import com.product.service.coreapi.events.promotion.PromotionCreatedEvent;
import com.product.service.coreapi.events.promotion.PromotionDeleteEvent;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class KafkaPublisherTopicRouter {

    private final Map<Class<?>, String> eventTopicMap = new HashMap<>();

    public KafkaPublisherTopicRouter() {

        eventTopicMap.put(ProductCreatedEvent.class, "productCreated");
        eventTopicMap.put(ProductDeletedEvent.class, "productDeleted");
        eventTopicMap.put(ProductInventoryUpdatedEvent.class, "productInventoryUpdated");
        eventTopicMap.put(ProductUpdatedEvent.class, "productUpdated");
        eventTopicMap.put(CategoryCreatedEvent.class, "categoryCreated");
        eventTopicMap.put(CategoryDeletedEvent.class, "categoryDeleted");
        eventTopicMap.put(CategoryUpdatedEvent.class, "categoryUpdated");
        eventTopicMap.put(PromotionCreatedEvent.class, "promotionCreated");
        eventTopicMap.put(PromotionDeleteEvent.class, "promotionDeleted");

    }

    public String getTopicForEvent(Object event) {
        return eventTopicMap.getOrDefault(event.getClass(), "DLQ");
    }
}
