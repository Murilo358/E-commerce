package com.product.service.kafka.processors.orders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.product.service.adapters.DateTimeConversion;
import com.product.service.coreapi.events.order.OrderCreatedEvent;
import com.product.service.kafka.processors.EventProcessor;
import com.product.service.kafka.processors.EventProcessorType;
import com.product.service.query.order.OrderRepository;
import com.product.service.query.order.OrderView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@EventProcessorType(OrderCreatedEvent.class)
public class OrderCreatedProcessor implements EventProcessor<OrderCreatedEvent> {

    private static final Logger log = LoggerFactory.getLogger(OrderCreatedProcessor.class);

    private final ObjectMapper mapper = new ObjectMapper();

    private final OrderRepository orderRepository;

    public OrderCreatedProcessor(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public void process(OrderCreatedEvent event) {

        JsonNode products = null;

        try {
            products = mapper.readTree(mapper.writeValueAsString(event.getProducts()));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        LocalDate updatedAt = Optional.ofNullable(event.getUpdatedAt())
                .map(DateTimeConversion::fromInstant)
                .map(LocalDateTime::toLocalDate)
                .orElse(null);

        LocalDate createdAt = Optional.ofNullable(event.getCreatedAt())
                .map(DateTimeConversion::fromInstant)
                .map(LocalDateTime::toLocalDate)
                .orElse(null);


        OrderView order = OrderView.builder()
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