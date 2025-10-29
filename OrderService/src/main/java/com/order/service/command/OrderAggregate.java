package com.order.service.command;

import com.order.service.coreapi.events.order.ApproveOrderCommand;
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

import java.time.LocalDate;
import java.time.OffsetDateTime;
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
    private OffsetDateTime createdAt;
    private OffsetDateTime canceledAt;
    private OffsetDateTime approvedAt;
    private OffsetDateTime shippedAt;
    private OffsetDateTime deliveredAt;
    private Object shippingInformation;
    private String notes; //todo implement
    private OffsetDateTime updatedAt;
    private OffsetDateTime deletedAt;

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
        return OrderProductState.builder()
                .categoryId(product.getCategory().id())
                .description(product.getDescription())
                .name(product.getName())
                .price(product.getPrice())
                .sellerId(product.getSeller().sellerId())
                .inventoryCount(product.getInventoryCount())
                .productId(product.getId())
                .build();
    }


    @EventSourcingHandler
    public void on(OrderCreationRequestedEvent event) {

        this.orderId = event.getOrderId();
        this.buyerId = event.getBuyerid();
        this.createdAt = OffsetDateTime.now();
        this.products = event.getProducts().stream()
                .map((p) ->
                    OrderProductState.builder()
                            .productId(p.productId())
                            .soldQuantity(p.quantity())
                            .build()
                )
                .toList();
        this.status = event.getOrderStatus();
    }



    @CommandHandler
    public void on(ApproveOrderCommand approveOrderCommand) {

        this.status = OrderStatus.APPROVED;
        this.products = approveOrderCommand.getProducts();
        this.totalPrice = approveOrderCommand.getTotalPrice();
        this.totalWeight = approveOrderCommand.getTotalWeight();
        this.buyerId = approveOrderCommand.getBuyerid();
        this.approvedAt = OffsetDateTime.now();
        this.shippingInformation = approveOrderCommand.getShippingInformation();

        ApproveOrderEvent approveOrderEvent = ApproveOrderEvent.builder()
                .orderId(orderId)
                .products(approveOrderCommand.getProducts())
                .buyerid(1L)
                .shippingInformation(null)//todo create table and get it
                .totalPrice(totalPrice)
                .totalWeight(totalWeight)
                .paymentMethod(approveOrderCommand.getPaymentMethod()) //todo create table and get it
                .buyerid(approveOrderCommand.getBuyerid())
                .canceledAt(null)
                .shippedAt(null)//todo implement shipping
                .deliveredAt(null)
                .approvedAt(LocalDate.now())
                .build();

        AggregateLifecycle.apply(approveOrderEvent);

    }

}
