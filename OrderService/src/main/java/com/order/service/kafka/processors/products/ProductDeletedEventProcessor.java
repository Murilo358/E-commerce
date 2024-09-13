package com.order.service.kafka.processors.products;

import com.order.service.coreapi.events.product.ProductCreatedEvent;
import com.order.service.coreapi.events.product.ProductDeletedEvent;
import com.order.service.kafka.processors.EventProcessor;
import com.order.service.kafka.processors.EventProcessorType;
import com.order.service.query.product.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@EventProcessorType(ProductDeletedEvent.class)
public class ProductDeletedEventProcessor implements EventProcessor<ProductDeletedEvent> {

    private static final Logger log = LoggerFactory.getLogger(ProductDeletedEventProcessor.class);

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void process(ProductDeletedEvent event) {
        productRepository.deleteById(event.getProductId());
        log.debug("Product with id: {} deleted successfully", event.getProductId());

    }
}
