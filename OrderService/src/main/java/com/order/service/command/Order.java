package com.order.service.command;

import com.order.service.query.product.ProductView;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.List;
import java.util.UUID;

@Aggregate
public class Order {

    @AggregateIdentifier
    private UUID orderId;
    private List<ProductView> products;
    private long paymentMethod;
    private long totalPrice;
}
