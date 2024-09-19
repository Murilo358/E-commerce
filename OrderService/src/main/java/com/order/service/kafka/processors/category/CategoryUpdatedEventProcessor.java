package com.order.service.kafka.processors.category;

import com.order.service.coreapi.events.category.CategoryUpdatedEvent;
import com.order.service.kafka.processors.EventProcessor;
import com.order.service.kafka.processors.EventProcessorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@EventProcessorType(CategoryUpdatedEvent.class)
public class CategoryUpdatedEventProcessor implements EventProcessor<CategoryUpdatedEvent> {

    private static final Logger log = LoggerFactory.getLogger(CategoryUpdatedEventProcessor.class);
    @Override
    public void process(CategoryUpdatedEvent event) {
        log.info("processed event: {}", event);
    }
}
