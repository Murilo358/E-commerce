package com.order.service.kafka.routers;

import com.order.service.kafka.processors.EventProcessorType;
import jakarta.annotation.PostConstruct;
import org.springframework.context.ApplicationContext;
import com.order.service.kafka.processors.EventProcessor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class EventProcessorRegistry {
    private final Map<Class<?>, EventProcessor<?>> processors = new HashMap<>();
    private final ApplicationContext applicationContext;

    public EventProcessorRegistry(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @PostConstruct
    public void init() {

        Map<String, Object> beansWithAnnotation = applicationContext.getBeansWithAnnotation(EventProcessorType.class);
        for (Object bean : beansWithAnnotation.values()) {
            EventProcessorType annotation = bean.getClass().getAnnotation(EventProcessorType.class);
            processors.put(annotation.value(), (EventProcessor<?>) bean);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> EventProcessor<T> getProcessor(Class<?> eventType) {
        return (EventProcessor<T>) processors.get(eventType);
    }
}
