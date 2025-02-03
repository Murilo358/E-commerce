package com.order.service.command;

import com.order.service.coreapi.commands.CreateOrderCommand;
import com.order.service.coreapi.commands.UpdateOrderStateCommand;
import com.order.service.coreapi.events.order.OrderCreatedEvent;
import com.order.service.coreapi.events.order.OrderProductState;
import com.order.service.coreapi.events.order.OrderStateUpdated;
import com.order.service.coreapi.events.order.OrderStatus;
import com.order.service.coreapi.queries.product.FindProductsByIdsQuery;
import com.order.service.dto.productDetail.OrderProduct;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

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


            List<OrderProduct>  productsView = queryGateway.query(
                    new FindProductsByIdsQuery(command.getProducts()),
                    ResponseTypes.multipleInstancesOf(OrderProduct.class)
            ).get();


            if(productsView == null || productsView.isEmpty()){
                log.error("Products not found with sent id's");
            }

            Double totalPrice = productsView.stream().map(OrderProduct::getPrice).filter(Objects::nonNull).reduce(0.0, Double::sum);
            Double totalWeight = productsView.stream().map(OrderProduct::getWeight).filter(Objects::nonNull).reduce(0.0, Double::sum);

            List<OrderProductState> products = new ArrayList<>();

            for (OrderProduct productView : productsView) {
                OrderProductState build = OrderProductState.newBuilder()
                        .setCategoryId(productView.getCategory().id())
                        .setDescription(productView.getDescription())
                        .setCreatedAt(productView.getCreatedAt().toLocalDate())
                        .setName(productView.getName())
                        .setPrice(productView.getPrice())
                        .setSellerId(productView.getSeller().sellerId())
                        .setInventoryCount(productView.getInventoryCount())
                        .setProductId(productView.getId()).build();
                products.add(build);
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
    @CommandHandler
    public void updateOrderState(UpdateOrderStateCommand updateOrderStateCommand){
        OrderStateUpdated build = OrderStateUpdated.newBuilder()
                .setId(updateOrderStateCommand.getOrderId())
                .setStatus(updateOrderStateCommand.getOrderStatus())
                .build();

        AggregateLifecycle.apply(build);
    }

    @EventSourcingHandler
    public void on(OrderStateUpdated event) {
        this.status = event.getStatus();
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
