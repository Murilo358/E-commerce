package com.order.service.command;


import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.UUID;


@Aggregate
public class Product {

    @AggregateIdentifier
    private UUID productId;
    private String name;
    private String description;
    private Double price;
    private Long sellerId;
    private UUID categoryId;
    private Integer inventoryCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    private static final Logger logger = LoggerFactory.getLogger(Product.class);

    protected Product() {
    }


}
