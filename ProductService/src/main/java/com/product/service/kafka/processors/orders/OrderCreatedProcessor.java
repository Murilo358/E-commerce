package com.product.service.kafka.processors.orders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.product.service.adapters.DateTimeConversion;
import com.product.service.coreapi.events.order.OrderCreatedEvent;
import com.product.service.kafka.processors.EventProcessor;
import com.product.service.kafka.processors.EventProcessorType;
import com.product.service.query.order.OrderRepository;
import com.product.service.query.order.OrderView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.OffsetDateTime;
import java.util.Optional;

@EventProcessorType(OrderCreatedEvent.class)
public class OrderCreatedProcessor implements EventProcessor<OrderCreatedEvent> {

    private static final Logger log = LoggerFactory.getLogger(OrderCreatedProcessor.class);

    private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    private final OrderRepository orderRepository;

    public OrderCreatedProcessor(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public void process(OrderCreatedEvent event) {

        JsonNode products = null;

        try {
            products = mapper.readTree(event.getProducts().toString( ));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        OffsetDateTime updatedAt = Optional.ofNullable(event.getUpdatedAt())
                .map(DateTimeConversion::fromInstant)
                .orElse(null);

        OffsetDateTime createdAt = Optional.ofNullable(event.getCreatedAt())
                .map(DateTimeConversion::fromInstant)
                .orElse(null);


        OrderView order = OrderView.builder()
                .paymentMethod(event.getPaymentMethod())
                .id(event.getId())
                .products(products)
                .totalPrice(event.getTotalPrice())
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .buyerId(event.getBuyerId())
                .status(event.getStatus())
                .weight(event.getWeight())
                .build();

        orderRepository.save(order);
        log.debug("Order created: {}", event);

    }
}