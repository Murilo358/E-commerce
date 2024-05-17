package com.product.service.command;

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
        AggregateLifecycle.apply(new ProductCreatedEvent(
                command.getProductId(),
                command.getName(),
                command.getDescription(),
                command.getPrice(),
                command.getSellerId(),
                command.getCategoryId(),
                command.getInventoryCount(),
                command.getCreatedAt(),
                command.getUpdatedAt())
        );
    }

    @CommandHandler
    public void handle(UpdateProductCommand command) {

        AggregateLifecycle.apply(
                ProductUpdatedEvent.builder()
                        .productId(productId)
                        .name(Optional.ofNullable(command.getName()).orElse(this.name))
                        .description(Optional.ofNullable(command.getDescription()).orElse(this.description))
                        .price(Optional.ofNullable(command.getPrice()).orElse(this.price))
                        .categoryId(Optional.ofNullable(command.getCategoryId()).orElse(this.categoryId))
                        .updatedAt(command.getUpdatedAt()).build()
        );
    }

    @CommandHandler
    public void handle(DeleteProductCommand command) {
        AggregateLifecycle.apply(
                ProductDeletedEvent.builder()
                        .productId(productId)
                        .deletedAt(command.getDeletedAt())
        );
    }

    @CommandHandler
    public void handle(UpdateProductInventoryCommand command) {
        AggregateLifecycle.apply(
                ProductInventoryUpdatedEvent.builder()
                        .productId(productId)
                        .inventoryCount(command.getInventoryCount())
                        .updatedAt(command.getUpdatedAt())
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
        this.createdAt = event.getCreatedAt();
        this.updatedAt = event.getUpdatedAt();
    }

    @EventSourcingHandler
    public void on(ProductUpdatedEvent event) {
        this.name = event.getName();
        this.description = event.getDescription();
        this.categoryId = event.getCategoryId();
        this.price = event.getPrice();
        this.updatedAt = event.getUpdatedAt();
    }

    @EventSourcingHandler
    public void on(ProductDeletedEvent event) {
        this.deletedAt = event.getDeletedAt();
        AggregateLifecycle.markDeleted();
    }

    @EventSourcingHandler
    public void on(ProductInventoryUpdatedEvent event) {
        this.inventoryCount = event.getInventoryCount();
    }

}
