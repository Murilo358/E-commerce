package com.order.service.kafka.processors.category;

import com.order.service.adapters.DateTimeConversion;
import com.order.service.coreapi.events.category.CategoryUpdatedEvent;
import com.order.service.kafka.processors.EventProcessor;
import com.order.service.kafka.processors.EventProcessorType;
import com.order.service.query.category.CategoryRepository;
import com.order.service.query.category.CategoryView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@EventProcessorType(CategoryUpdatedEvent.class)
public class CategoryUpdatedEventProcessor implements EventProcessor<CategoryUpdatedEvent> {

    @Autowired
    CategoryRepository categoryRepository;

    private static final Logger log = LoggerFactory.getLogger(CategoryUpdatedEventProcessor.class);
    @Override
    public void process(CategoryUpdatedEvent event) {
        LocalDateTime localDateTime = DateTimeConversion.fromInstant(event.getUpdatedAt());
        CategoryView categoryView = CategoryView.builder()
                .id(event.getId())
                .name(event.getName())
                .description(event.getDescription())
                .updatedAt(localDateTime)
               .build();

        categoryRepository.save(categoryView);
        log.debug("category updated: {}", event);
    }
}
