package com.order.service.config.kafka.tests;

import com.order.service.coreapi.events.product.ProductCreatedEvent;
import com.order.service.coreapi.events.product.ProductDeletedEvent;

public class Processors {
    public static class ProductCreatedEventProcessor implements EventProcessor<ProductCreatedEvent> {
        @Override
        public void process(ProductCreatedEvent event) {
            System.out.println("Processing ProductCreatedEvent: " + event);
        }
    }

    public static class ProductDeletedEventProcessor implements EventProcessor<ProductDeletedEvent> {
        @Override
        public void process(ProductDeletedEvent event) {
            System.out.println("Processing ProductDeletedEvent: " + event);
        }
    }


}
