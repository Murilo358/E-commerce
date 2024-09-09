package com.product.service.command;

import com.product.service.adapters.DateTimeConversion;
import com.product.service.coreapi.commands.product.CreateProductCommand;
import com.product.service.coreapi.commands.product.DeleteProductCommand;
import com.product.service.coreapi.commands.product.UpdateProductCommand;
import com.product.service.coreapi.commands.product.UpdateProductInventoryCommand;
import com.product.service.coreapi.events.product.ProductCreatedEvent;
import com.product.service.coreapi.events.product.ProductDeletedEvent;
import com.product.service.coreapi.events.product.ProductInventoryUpdatedEvent;
import com.product.service.coreapi.events.product.ProductUpdatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;
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

    @CommandHandler
    public Product(CreateProductCommand command) {


        ProductCreatedEvent build = ProductCreatedEvent
                .newBuilder()
                .setProductId(command.getProductId())
                .setName(command.getName())
                .setDescription(command.getDescription())
                .setPrice(command.getPrice())
                .setSellerId(command.getSellerId())
                .setCategoryId(command.getCategoryId())
                .setInventoryCount(command.getInventoryCount())
                .setCreatedAt(command.getCreatedAt() != null ? command.getCreatedAt().toInstant(ZoneOffset.UTC) : null)
                .setUpdatedAt(command.getUpdatedAt() != null ? command.getUpdatedAt().toInstant(ZoneOffset.UTC) : null).build();

        AggregateLifecycle.apply(build);


    }

    @CommandHandler
    public void handle(UpdateProductCommand command) {

        AggregateLifecycle.apply(
                ProductUpdatedEvent.newBuilder()
                        .setProductId(productId)
                        .setName(Optional.ofNullable(command.getName()).orElse(this.name))
                        .setDescription(Optional.ofNullable(command.getDescription()).orElse(this.description))
                        .setPrice(Optional.ofNullable(command.getPrice()).orElse(this.price))
                        .setCategoryId(Optional.ofNullable(command.getCategoryId()).orElse(this.categoryId))
                        .setUpdatedAt(command.getUpdatedAt().toInstant(ZoneOffset.UTC)).build()
        );
    }

    @CommandHandler
    public void handle(DeleteProductCommand command) {
        AggregateLifecycle.apply(
                ProductDeletedEvent.newBuilder()
                        .setProductId(productId)
                        .setDeletedAt(command.getDeletedAt().toInstant(ZoneOffset.UTC)).build()
        );
    }

    @CommandHandler
    public void handle(UpdateProductInventoryCommand command) {
        AggregateLifecycle.apply(
                ProductInventoryUpdatedEvent.newBuilder()
                        .setProductId(productId)
                        .setInventoryCount(command.getInventoryCount())
                        .setUpdatedAt(command.getUpdatedAt().toInstant(ZoneOffset.UTC))
                        .build());
    }


    @EventSourcingHandler
    public void on(ProductCreatedEvent event) {
        this.productId = event.getProductId();
        this.name = event.getName();
        this.description = event.getDescription();
        this.price = event.getPrice();
        this.sellerId = event.getSellerId();
        this.categoryId = event.getCategoryId();
        this.inventoryCount = event.getInventoryCount();
        this.createdAt = DateTimeConversion.fromInstant(event.getCreatedAt());
        this.updatedAt = DateTimeConversion.fromInstant(event.getUpdatedAt());

    }


    @EventSourcingHandler
    public void on(ProductUpdatedEvent event) {
        this.name = event.getName();
        this.description = event.getDescription();
        this.categoryId = event.getCategoryId();
        this.price = event.getPrice();
        this.updatedAt =  DateTimeConversion.fromInstant(event.getUpdatedAt());
    }

    @EventSourcingHandler
    public void on(ProductDeletedEvent event) {
        this.deletedAt = DateTimeConversion.fromInstant(event.getDeletedAt());
        AggregateLifecycle.markDeleted();
    }

    @EventSourcingHandler
    public void on(ProductInventoryUpdatedEvent event) {
        this.inventoryCount = event.getInventoryCount();
    }

}
