package com.product.service.kafka.routers;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class EventTypeRegistry {

    private final Map<String, Class<?>> eventTypeMap = new HashMap<>();

    @PostConstruct
    public void init() {
        try (ScanResult scanResult = new ClassGraph()
                .acceptPackages("com.product.service.coreapi.events")
                .scan()) {

            scanResult.getAllClasses()
                    .filter(classInfo -> classInfo.getName().endsWith("Event"))
                    .forEach(classInfo -> {
                        eventTypeMap.put(classInfo.getSimpleName(), classInfo.loadClass());
                    });
        }
    }

    public Class<?> getEventClass(String eventType) {
        return eventTypeMap.get(eventType);
    }
}