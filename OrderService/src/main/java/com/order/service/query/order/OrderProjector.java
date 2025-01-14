package com.order.service.query.order;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.order.service.coreapi.events.order.OrderCreatedEvent;
import com.order.service.coreapi.events.order.OrderProductState;
import com.order.service.coreapi.events.order.OrderStateUpdated;
import com.order.service.coreapi.events.order.OrderStatus;
import com.order.service.kafka.publisher.KafkaPublisher;
import com.order.service.query.product.ProductRepository;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ProcessingGroup("events")
@Component
public class OrderProjector {

    private final OrderRepository orderRepository;

    private final ProductRepository productRepository;

    private final KafkaPublisher kafkaPublisher;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public OrderProjector(OrderRepository orderRepository, ProductRepository productRepository, KafkaPublisher kafkaPublisher) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.kafkaPublisher = kafkaPublisher;
    }

    @EventHandler
    public void on(OrderCreatedEvent event) {
        JsonNode products = null;

        try {
            products = objectMapper.readTree(objectMapper.writeValueAsString(event.getProducts()));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        OrderView orderView = OrderView.builder()
                .id(event.getId())
                .paymentMethod(event.getPaymentMethod())
                .products(products)
                .totalPrice(event.getTotalPrice())
                .createdAt(LocalDate.from(event.getCreatedAt()))
                .updatedAt(LocalDate.from(event.getUpdatedAt()))
                .buyerId(event.getBuyerId())
                .status(event.getStatus())
                .weight(event.getWeight())
                .build();

        orderRepository.save(orderView);

        kafkaPublisher.send(String.valueOf(event.getId()), event);

    }

    @EventHandler
    public void on (OrderStateUpdated event){

        orderRepository.findById(event.getId()).ifPresent(order -> {
            order.setStatus(event.getStatus());

            if(event.getStatus().equals(OrderStatus.APPROVED)){
                JsonNode eventProducts = order.getProducts();

                List<OrderProductState> orderProductState = null;
                try {
                    orderProductState = objectMapper.readValue(eventProducts.toString(),  new TypeReference<List<OrderProductState>>() {});
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }

                List<UUID> productIds = Optional.ofNullable(orderProductState)
                        .orElse(List.of())
                        .stream()
                        .map(OrderProductState::getProductId).toList();

                productRepository.updateInventoryCountByIdIn(productIds);
            }

            orderRepository.save(order);
        });



        kafkaPublisher.send(String.valueOf(event.getId()), event);
    }
}
