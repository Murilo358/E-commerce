package com.order.service.command;

import com.order.service.coreapi.commands.CreateOrderCommand;
import com.order.service.coreapi.queries.product.FindProductsQuery;
import com.order.service.query.product.ProductView;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Aggregate
public class Order {

    @AggregateIdentifier
    private UUID orderId;
    private List<ProductView> products;
    private long paymentMethod;
    private long totalPrice;

    protected Order() {}

    @CommandHandler
    public Order(CreateOrderCommand command, QueryGateway queryGateway) {

        try {
            List<ProductView> productsView = queryGateway.query(
                    new FindProductsQuery(command.getProducts()),
                    ResponseTypes.multipleInstancesOf(ProductView.class)
            ).get();



        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

    }
}
