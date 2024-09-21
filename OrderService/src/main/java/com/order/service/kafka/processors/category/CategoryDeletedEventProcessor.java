package com.order.service.kafka.processors.category;

import com.order.service.coreapi.events.category.CategoryDeletedEvent;
import com.order.service.kafka.processors.EventProcessor;
import com.order.service.kafka.processors.EventProcessorType;
import com.order.service.query.category.CategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@EventProcessorType(CategoryDeletedEvent.class)
public class CategoryDeletedEventProcessor implements EventProcessor<CategoryDeletedEvent> {
    private static final Logger log = LoggerFactory.getLogger(CategoryDeletedEventProcessor.class);

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public void process(CategoryDeletedEvent event) {

        categoryRepository.deleteById(event.getId());

        log.info("Category deleted: {}", event);
    }
}
