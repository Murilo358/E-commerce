package com.order.service.query.order;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.order.service.config.jackson.JacksonAvroModule;
import com.order.service.coreapi.events.order.ApproveOrderEvent;
import com.order.service.coreapi.events.order.OrderCreationRequestedEvent;
import com.order.service.kafka.publisher.KafkaPublisher;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;

@Slf4j
@ProcessingGroup("events")
@Component
public class OrderProjector {

    private final OrderRepository orderRepository;

    private final KafkaPublisher kafkaPublisher;

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule()).registerModule(new JacksonAvroModule());

    public OrderProjector(OrderRepository orderRepository, KafkaPublisher kafkaPublisher) {
        this.orderRepository = orderRepository;
        this.kafkaPublisher = kafkaPublisher;
    }

    @EventHandler
    public void on(OrderCreationRequestedEvent event) {

        OrderView orderView = OrderView.builder()
                .id(event.getOrderId())
                .createdAt(LocalDate.now())
                .status(event.getOrderStatus()).build();


        orderRepository.save(orderView);
        kafkaPublisher.send(String.valueOf(event.getOrderId()), event);//todo arrumar
    }

    @EventHandler
    public void on(ApproveOrderEvent event) {

        JsonNode products = null;
        JsonNode shippingInformation = null;

        Optional<OrderView> orderViewOpt = orderRepository.findById(event.getOrderId());

        if (orderViewOpt.isPresent()) {

            OrderView orderView = orderViewOpt.get();

            try {
                products = objectMapper.readTree(objectMapper.writeValueAsString(event.getProducts()));
                shippingInformation = objectMapper.readTree(objectMapper.writeValueAsString(event.getShippingInformation()));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

            orderView.setId(event.getOrderId());
            orderView.setBuyerId(event.getBuyerid());
            orderView.setProducts(products);
            orderView.setTotalPrice(event.getTotalPrice());
            orderView.setWeight(event.getTotalWeight());
            orderView.setStatus(event.getOrderStatus());
            orderView.setShippingInfo(shippingInformation);
            orderView.setCanceledAt(event.getCanceledAt());
            orderView.setApprovedAt(event.getApprovedAt());
            orderView.setCreatedAt(event.getCreatedAt());
//          orderView.setPaymentMethod(event.getPaymentMethod()) //todo precisa ser um estado? provavelmente

            orderRepository.save(orderView);

            kafkaPublisher.send(String.valueOf(event.getOrderId()), event);//todo arrumar //todo tratar de outro jeito, talvez ele receber outro objeto e ai sim criar para enviar no kafka

        }
    }


}
