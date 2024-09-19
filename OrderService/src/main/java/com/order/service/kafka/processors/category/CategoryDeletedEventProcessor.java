package com.order.service.kafka.processors.category;

import com.order.service.coreapi.events.category.CategoryDeletedEvent;
import com.order.service.kafka.processors.EventProcessor;
import com.order.service.kafka.processors.EventProcessorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@EventProcessorType(CategoryDeletedEvent.class)
public class CategoryDeletedEventProcessor implements EventProcessor<CategoryDeletedEvent> {
    private static final Logger log = LoggerFactory.getLogger(CategoryDeletedEventProcessor.class);
    @Override
    public void process(CategoryDeletedEvent event) {
        log.info("processed event: {}", event);
    }
}
