package com.order.service.query.order;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.order.service.adapters.DateTimeConversion;
import com.order.service.config.jackson.JacksonAvroModule;
import com.order.service.coreapi.events.order.*;
import com.order.service.kafka.publisher.KafkaPublisher;
import com.order.service.query.product.ProductRepository;
import com.order.service.query.salesmetrics.SalesMetricsRepository;
import com.order.service.query.salesmetrics.SalesMetricsView;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@ProcessingGroup("events")
@Component
public class OrderProjector {

    private final OrderRepository orderRepository;

    private final SalesMetricsRepository salesMetricsRepository;

    private final ProductRepository productRepository;

    private final KafkaPublisher kafkaPublisher;

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule()).registerModule(new JacksonAvroModule());

    public OrderProjector(OrderRepository orderRepository, SalesMetricsRepository salesMetricsRepository, ProductRepository productRepository, KafkaPublisher kafkaPublisher) {
        this.orderRepository = orderRepository;
        this.salesMetricsRepository = salesMetricsRepository;
        this.productRepository = productRepository;
        this.kafkaPublisher = kafkaPublisher;
    }

    @EventHandler
    public void on(OrderCreatedEvent event) {
        JsonNode products = null;

        if(event.getProducts() == null || event.getProducts().isEmpty()) {
            log.error("Received order with empty products");
            return;
        }

        try {
            products = objectMapper.readTree(objectMapper.writeValueAsString(event.getProducts()));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        for (OrderProductState product : event.getProducts()) {

            UUID productId = product.getProductId();
            SalesMetricsView metrics = salesMetricsRepository.findById(productId)
                    .orElse(SalesMetricsView.builder()
                            .productId(productId)
                            .totalSold(0)
                            .soldLastMonth(0)
                            .lastSold(null)
                            .build());

            metrics.setTotalSold(metrics.getTotalSold() + product.getQuantity());
            OffsetDateTime offsetDateTime = DateTimeConversion.fromInstantToOffset(event.getCreatedAt());
            metrics.setLastSold(offsetDateTime);

            salesMetricsRepository.save(metrics);


        }

        OrderView orderView = OrderView.builder()
                .id(event.getId())
                .paymentMethod(event.getPaymentMethod())
                .products(products)
                .totalPrice(event.getTotalPrice())
                .createdAt(event.getCreatedAt() != null ? LocalDate.ofInstant(event.getCreatedAt(), ZoneId.of("UTC")) : null)
                .updatedAt(event.getUpdatedAt() != null ? LocalDate.ofInstant(event.getUpdatedAt(), ZoneId.of("UTC")) : null)
                .buyerId(event.getBuyerId())
                .status(event.getStatus())
                .weight(event.getWeight())
                .sellerId(1L)
                .build();

        orderRepository.save(orderView);

        kafkaPublisher.send(String.valueOf(event.getId()), event);

    }

    @EventHandler
    public void on (OrderStateUpdated event){

        orderRepository.findById(event.getId()).ifPresent(order -> {

            OrderUpdatedStatus status = event.getStatus();
            OrderStatus orderStatus = OrderStatus.valueOf(String.valueOf(status));
            order.setStatus(orderStatus);

            if(orderStatus.equals(OrderStatus.APPROVED)){
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
