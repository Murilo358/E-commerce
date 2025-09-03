package com.order.service.command;

import com.order.service.coreapi.commands.CreateOrderCommand;
import com.order.service.coreapi.commands.UpdateOrderStateCommand;
import com.order.service.coreapi.events.order.*;
import com.order.service.coreapi.queries.product.FindProductsByIdsQuery;
import com.order.service.dto.productDetail.OrderProduct;
import com.order.service.gui.order.dto.OrderProductDTO;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.spring.stereotype.Aggregate;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Slf4j
@Aggregate
public class Order {

    @AggregateIdentifier
    private UUID orderId;
    private List<OrderProductState> products;
    private long paymentMethod;
    private long buyerId;
    private double totalPrice;
    private double totalWeight;
    private OrderStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    protected Order() {}

    @CommandHandler
    public Order(CreateOrderCommand command, QueryGateway queryGateway) {

        try {

            List<UUID> productsIds = command.getProducts().stream().map(OrderProductDTO::productId).toList();

            Map<UUID, Long> collect = command.getProducts().stream().collect(Collectors.toMap(OrderProductDTO::productId, OrderProductDTO::quantity));

            List<OrderProduct>  productsView = queryGateway.query(
                    new FindProductsByIdsQuery(productsIds),
                    ResponseTypes.multipleInstancesOf(OrderProduct.class)
            ).get();


            if(productsView == null || productsView.isEmpty()){
                log.error("Products not found with sent id's  {} ", productsIds);
                return;
            }

            double totalPrice = productsView.stream()
                    .mapToDouble(i -> i.getPrice() * collect.get(i.getId()))
                    .sum();

            double totalWeight = productsView.stream()
                    .filter(i -> i.getWeight() != null)
                    .mapToDouble(i -> i.getWeight() * collect.get(i.getId()))
                    .sum();
            List<OrderProductState> products = new ArrayList<>();

            for (OrderProduct productView : productsView) {
                products.add(toState(productView));
            }

            OrderCreatedEvent build = OrderCreatedEvent.newBuilder()
                    .setId(command.getOrderId())
                    .setBuyerId(command.getBuyerid())
                    .setProducts(products)
                    .setId(command.getOrderId())
                    .setPaymentMethod(command.getPaymentMethod())
                    .setTotalPrice(totalPrice)
                    .setWeight(totalWeight)
                    .setCreatedAt(Instant.now())
                    .setStatus(OrderStatus.PENDING)
                    .build();

            AggregateLifecycle.apply(build);


        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

    }

    private OrderProductState toState(OrderProduct product) {
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


    @CommandHandler
    public void updateOrderState(UpdateOrderStateCommand updateOrderStateCommand){
        OrderStateUpdated build = OrderStateUpdated.newBuilder()
                .setId(updateOrderStateCommand.getOrderId())
                .setStatus(OrderUpdatedStatus.valueOf(String.valueOf(updateOrderStateCommand.getOrderStatus())))
                .build();

        AggregateLifecycle.apply(build);
    }

    @EventSourcingHandler
    public void on(OrderStateUpdated event) {
        this.status = OrderStatus.valueOf(String.valueOf(event.getStatus()));
    }

    @EventSourcingHandler
    public void on(OrderCreatedEvent event) {

        this.paymentMethod = event.getPaymentMethod();
        this.status = event.getStatus();
        this.orderId = event.getId();
        this.products = event.getProducts();
        this.totalPrice = event.getTotalPrice();
        this.totalWeight = event.getWeight();
        this.buyerId = event.getBuyerId();
        this.createdAt = event.getCreatedAt() != null ? LocalDateTime.ofInstant(event.getCreatedAt(), ZoneId.of("UTC")) : null;
        this.updatedAt = event.getUpdatedAt() != null ? LocalDateTime.ofInstant(event.getUpdatedAt(), ZoneId.of("UTC")) : null;

    }

}
