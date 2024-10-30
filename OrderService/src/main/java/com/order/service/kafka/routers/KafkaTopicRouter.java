package com.order.service.kafka.routers;

import com.order.service.coreapi.events.category.CategoryCreatedEvent;
import com.order.service.coreapi.events.category.CategoryDeletedEvent;
import com.order.service.coreapi.events.category.CategoryUpdatedEvent;
import com.order.service.coreapi.events.order.OrderCreatedEvent;
import com.order.service.coreapi.events.product.ProductCreatedEvent;
import com.order.service.coreapi.events.product.ProductDeletedEvent;
import com.order.service.coreapi.events.product.ProductInventoryUpdatedEvent;
import com.order.service.coreapi.events.product.ProductUpdatedEvent;
import com.order.service.coreapi.events.promotion.PromotionCreatedEvent;
import com.order.service.coreapi.events.promotion.PromotionDeleteEvent;
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
        eventTopicMap.put(CategoryCreatedEvent.class, "categoryCreated");
        eventTopicMap.put(CategoryDeletedEvent.class, "categoryDeleted");
        eventTopicMap.put(CategoryUpdatedEvent.class, "categoryUpdated");
        eventTopicMap.put(PromotionCreatedEvent.class, "promotionCreated");
        eventTopicMap.put(PromotionDeleteEvent.class, "promotionDeleted");
        eventTopicMap.put(OrderCreatedEvent.class, "orderCreated");

    }

    public String getTopicForEvent(Object event) {
        return eventTopicMap.getOrDefault(event.getClass(), "DLQ");
    }
}
