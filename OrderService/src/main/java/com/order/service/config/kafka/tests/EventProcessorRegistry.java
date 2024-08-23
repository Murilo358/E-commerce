package com.order.service.config.kafka.tests;

import com.order.service.coreapi.events.product.ProductCreatedEvent;
import com.order.service.coreapi.events.product.ProductDeletedEvent;

import java.util.HashMap;
import java.util.Map;

public class EventProcessorRegistry {

    private final Map<Object, EventProcessor<?>> processors = new HashMap<>();

    public EventProcessorRegistry() {

        registerProcessor(ProductCreatedEvent.class, new Processors.ProductCreatedEventProcessor());
        registerProcessor(ProductDeletedEvent.class, new Processors.ProductDeletedEventProcessor());
    }

    public <T> void registerProcessor(Class<T> eventType, EventProcessor<? super T> processor) {
        processors.put(eventType, processor);
    }

    @SuppressWarnings("unchecked")
    public <T> EventProcessor<T> getProcessor(Object eventType) {
        return (EventProcessor<T>) processors.get(eventType);
    }
}
