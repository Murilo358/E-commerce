package com.order.service.kafka.processors.category;

import com.order.service.coreapi.events.category.CategoryCreatedEvent;
import com.order.service.kafka.processors.EventProcessor;
import com.order.service.kafka.processors.EventProcessorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@EventProcessorType(CategoryCreatedEvent.class)
public class CategoryCreatedEventProcessor implements EventProcessor<CategoryCreatedEvent> {
    private static final Logger log = LoggerFactory.getLogger(CategoryCreatedEventProcessor.class);

    @Override
    public void process(CategoryCreatedEvent event) {
        log.info("processed event: {}", event);
    }
}
