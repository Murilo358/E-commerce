package com.order.service.kafka.routers;

import jakarta.annotation.PostConstruct;
import org.reflections.Reflections;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class EventTypeRegistry {

    private final Map<String, Class<?>> eventTypeMap = new HashMap<>();

    @PostConstruct
    public void init() {
        String basePackage = "com.order.service.coreapi.events";

        Reflections reflections = new Reflections(basePackage);

        Set<Class<?>> eventClasses = reflections.getSubTypesOf(Object.class);

        for (Class<?> eventClass : eventClasses) {
            if (eventClass.getSimpleName().endsWith("Event")) {
                eventTypeMap.put(eventClass.getSimpleName(), eventClass);
            }
        }
    }

    public Class<?> getEventClass(String eventType) {
        return eventTypeMap.get(eventType);
    }
}