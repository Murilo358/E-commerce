package com.order.service.query.order;

import com.order.service.coreapi.events.order.OrderCreatedEvent;
import com.order.service.kafka.publisher.KafkaPublisher;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@ProcessingGroup("events")
@Component
public class OrderProjector {

    private final OrderRepository orderRepository;

    private final KafkaPublisher kafkaPublisher;

    public OrderProjector(OrderRepository orderRepository, KafkaPublisher kafkaPublisher) {
        this.orderRepository = orderRepository;
        this.kafkaPublisher = kafkaPublisher;
    }

    @EventHandler
    public void on(OrderCreatedEvent event) {

        OrderView orderView = OrderView.builder()
                .id(event.getId())
                .paymentMethod(event.getPaymentMethod())
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
}
