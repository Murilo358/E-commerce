package com.order.service.kafka.processors.products;

import com.order.service.adapters.DateTimeConversion;
import com.order.service.coreapi.events.product.ProductCreatedEvent;
import com.order.service.kafka.processors.EventProcessor;
import com.order.service.kafka.processors.EventProcessorType;
import com.order.service.query.product.ProductRepository;
import com.order.service.query.product.ProductView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


@EventProcessorType(ProductCreatedEvent.class)
public class ProductCreatedEventProcessor implements EventProcessor<ProductCreatedEvent> {

    private static final Logger log = LoggerFactory.getLogger(ProductCreatedEventProcessor.class);

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void process(ProductCreatedEvent event) {
        ProductView productView = ProductView.builder()
                .id(event.getProductId())
                .name(event.getName())
                .description(event.getDescription())
                .price(event.getPrice())
                .sellerId(event.getSellerId())
                .categoryId(event.getCategoryId())
                .inventoryCount(event.getInventoryCount())
                .createdAt(event.getCreatedAt() != null ? DateTimeConversion.fromInstant(event.getCreatedAt()) : null)
                .updatedAt(event.getUpdatedAt() != null ? DateTimeConversion.fromInstant(event.getUpdatedAt()) : null)
                .build();

        productRepository.save(productView);
        log.debug("Product created: {}", event);
    }
}