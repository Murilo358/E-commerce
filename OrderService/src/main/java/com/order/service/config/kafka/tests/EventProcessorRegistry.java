package com.order.service.config.kafka.tests;

import com.order.service.coreapi.events.product.ProductCreatedEvent;
import com.order.service.coreapi.events.product.ProductDeletedEvent;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class EventProcessorRegistry {

    private final Map<Class<?>, EventProcessor<?>> processors = new HashMap<>();

    public EventProcessorRegistry() {

        registerProcessor(ProductCreatedEvent.class, new Processors.ProductCreatedEventProcessor());
        registerProcessor(ProductDeletedEvent.class, new Processors.ProductDeletedEventProcessor());
    }

    public <T> void registerProcessor(Class<T> eventType, EventProcessor<? super T> processor) {
        processors.put(eventType, processor);
    }

    @SuppressWarnings("unchecked")
    public <T> EventProcessor<T> getProcessor(Class<?> eventType) {
        return (EventProcessor<T>) processors.get(eventType);
    }
}
