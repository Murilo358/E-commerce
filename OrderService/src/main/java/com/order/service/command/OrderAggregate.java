package com.order.service.command;

import com.order.service.coreapi.events.order.ApproveOrderEvent;
import com.order.service.coreapi.events.order.OrderCreationRequestedEvent;
import com.order.service.coreapi.commands.order.RequestOrderCreationCommand;
import com.order.service.coreapi.events.order.*;
import com.order.service.dto.productDetail.OrderProduct;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Aggregate
@SuppressWarnings({"unused", "FieldCanBeLocal"})
public class OrderAggregate {

    @AggregateIdentifier
    private UUID orderId;
    private List<OrderProductState> products;
    private long paymentMethod;
    private long buyerId;
    private double totalPrice;
    private double totalWeight;
    private OrderStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime canceledAt;
    private LocalDateTime approvedAt;
    private LocalDateTime shippedAt;
    private LocalDateTime deliveredAt;
    private Object shippingInformation;
    private String notes; //todo implement
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    protected OrderAggregate() {}

    @CommandHandler
    public OrderAggregate(RequestOrderCreationCommand command) {

        OrderCreationRequestedEvent orderCreationRequestedEvent = OrderCreationRequestedEvent
                .builder()
                .orderId(command.getOrderId())
                .products(command.getProducts())
                .shippingInformationId(command.getShippingInformationId())
                .paymentMethod(command.getPaymentMethod())
                .orderStatus(OrderStatus.PENDING)
                .buyerid(command.getBuyerid()).build();

        AggregateLifecycle.apply(orderCreationRequestedEvent);


    }


    private OrderProductState createOrderProductState(OrderProduct product) {
        return OrderProductState.newBuilder()
                .setCategoryId(product.getCategory().id())
                .setDescription(product.getDescription())
                .setCreatedAt(product.getCreatedAt().toLocalDate())
                .setName(product.getName())
                .setPrice(product.getPrice())
                .setSellerId(product.getSeller().sellerId())
                .setInventoryCount(product.getInventoryCount())
                .setProductId(product.getId())
                .build();
    }


    @EventSourcingHandler
    public void on(OrderCreationRequestedEvent event) {

        this.orderId = event.getOrderId();
        this.buyerId = event.getBuyerid();
        this.createdAt = LocalDateTime.now(); // todo use OffsetDateTime??
        this.products = event.getProducts().stream()
                .map((p) -> {
                    OrderProductState orderProductState = new OrderProductState();
                    orderProductState.setProductId(p.productId());
                    orderProductState.setQuantity(Math.toIntExact(p.quantity())); //todo fazer o objeto ser buildado como long e nao int
                    return orderProductState;
                })
                .toList();
        this.status = event.getOrderStatus();
    }



    @EventSourcingHandler
    public void on(ApproveOrderEvent approveOrderEvent) {

        this.status = OrderStatus.APPROVED;
        this.products = approveOrderEvent.getProducts().stream().map(this::createOrderProductState).toList();
        this.totalPrice = approveOrderEvent.getTotalPrice();
        this.totalWeight = approveOrderEvent.getTotalWeight();
        this.buyerId = approveOrderEvent.getBuyerid();
        this.approvedAt = LocalDateTime.now();
        this.shippingInformation = approveOrderEvent.getShippingInformation();

    }

}
