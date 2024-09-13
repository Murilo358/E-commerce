package com.order.service.kafka.processors.products;

import com.order.service.adapters.DateTimeConversion;
import com.order.service.coreapi.events.product.ProductInventoryUpdatedEvent;
import com.order.service.coreapi.events.product.ProductUpdatedEvent;
import com.order.service.kafka.processors.EventProcessor;
import com.order.service.kafka.processors.EventProcessorType;
import com.order.service.query.product.ProductRepository;
import com.order.service.query.product.ProductView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@EventProcessorType(ProductUpdatedEvent.class)
public class ProductUpdatedEventProcessor implements EventProcessor<ProductUpdatedEvent> {

    private static final Logger log = LoggerFactory.getLogger(ProductUpdatedEventProcessor.class);

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void process(ProductUpdatedEvent event) {
        ProductView productView = ProductView.builder()
                .id(event.getProductId())
                .name(event.getName())
                .description(event.getDescription())
                .price(event.getPrice())
                .categoryId(event.getCategoryId())
                .updatedAt(event.getUpdatedAt() != null ? DateTimeConversion.fromInstant(event.getUpdatedAt()) : null)
                .build();

        productRepository.save(productView);
        log.debug("Product with id: {} updated successfully", event.getProductId());
    }
}
