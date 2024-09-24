package com.order.service.kafka.processors.products;

import com.order.service.coreapi.events.product.ProductInventoryUpdatedEvent;
import com.order.service.kafka.processors.EventProcessor;
import com.order.service.kafka.processors.EventProcessorType;
import com.order.service.query.product.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@EventProcessorType(ProductInventoryUpdatedEvent.class)
public class ProductIntentoryUpdatedEventProcessor implements EventProcessor<ProductInventoryUpdatedEvent> {

    private static final Logger log = LoggerFactory.getLogger(ProductIntentoryUpdatedEventProcessor.class);

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void process(ProductInventoryUpdatedEvent event) {
        productRepository.updateInventoryCountById(event.getInventoryCount(), event.getProductId());
        log.debug("Product with id: {} updated inventory count to: {}", event.getProductId(), event.getInventoryCount());
    }

}
