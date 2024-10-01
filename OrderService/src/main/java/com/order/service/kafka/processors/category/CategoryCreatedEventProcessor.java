package com.order.service.kafka.processors.category;

import com.order.service.adapters.DateTimeConversion;
import com.order.service.coreapi.events.category.CategoryCreatedEvent;
import com.order.service.kafka.processors.EventProcessor;
import com.order.service.kafka.processors.EventProcessorType;
import com.order.service.query.category.CategoryRepository;
import com.order.service.query.category.CategoryView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


@Component
@EventProcessorType(CategoryCreatedEvent.class)
public class CategoryCreatedEventProcessor implements EventProcessor<CategoryCreatedEvent> {
    private static final Logger log = LoggerFactory.getLogger(CategoryCreatedEventProcessor.class);

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public void process(CategoryCreatedEvent event) {
        LocalDateTime createdAt = DateTimeConversion.fromInstant(event.getCreatedAt());
        CategoryView categoryView = CategoryView.builder()
                .id(event.getId())
                .name(event.getName())
                .description(event.getDescription())
                .createdAt(createdAt)
                .systemDefault(event.getSystemDefault()).build();

        categoryRepository.save(categoryView);
        log.debug("Category created: {}", event);
    }
}
