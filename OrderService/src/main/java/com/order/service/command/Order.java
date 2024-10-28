package com.order.service.command;

import com.order.service.coreapi.commands.CreateOrderCommand;
import com.order.service.coreapi.events.order.OrderCreatedEvent;
import com.order.service.coreapi.events.order.OrderProductState;
import com.order.service.coreapi.queries.product.FindProductsQuery;
import com.order.service.query.product.ProductView;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.spring.stereotype.Aggregate;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Aggregate
public class Order {

    @AggregateIdentifier
    private UUID orderId;
    private List<OrderProductState> products;
    private long paymentMethod;
    private long buyerId;
    private double totalPrice;
    private double totalWeight;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    protected Order() {}

    @CommandHandler
    public Order(CreateOrderCommand command, QueryGateway queryGateway) {

        try {
            List<ProductView> productsView = queryGateway.query(
                    new FindProductsQuery(command.getProducts()),
                    ResponseTypes.multipleInstancesOf(ProductView.class)
            ).get();

            Double totalPrice = productsView.stream().map(ProductView::getPrice).reduce(0.0, Double::sum);
            Double totalWeight = productsView.stream().map(ProductView::getWeight).reduce(0.0, Double::sum);

            List<OrderProductState> products = new ArrayList<>();

            for (ProductView productView : productsView) {
                OrderProductState build = OrderProductState.newBuilder()
                        .setCategoryId(productView.getCategoryId())
                        .setDescription(productView.getDescription())
                        .setCreatedAt(productView.getCreatedAt().toLocalDate())
                        .setName(productView.getName())
                        .setPrice(productView.getPrice())
                        .setSellerId(productView.getSellerId())
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
                    .setCreatedAt(Instant.now()).build();

            AggregateLifecycle.apply(build);


        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

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
        this.createdAt = LocalDateTime.from(event.getCreatedAt());
        this.updatedAt = LocalDateTime.from(event.getUpdatedAt());

    }

}
