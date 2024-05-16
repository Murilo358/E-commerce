package com.product.service.command;

import com.product.service.coreapi.commands.CreateProductCommand;
import com.product.service.coreapi.commands.DeleteProductCommand;
import com.product.service.coreapi.commands.UpdateProductCommand;
import com.product.service.coreapi.commands.UpdateProductInventoryCommand;
import com.product.service.coreapi.events.ProductCreatedEvent;
import com.product.service.coreapi.events.ProductDeletedEvent;
import com.product.service.coreapi.events.ProductInventoryUpdatedEvent;
import com.product.service.coreapi.events.ProductUpdatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.UUID;


@Aggregate
public class Product {
    private String name;
    private String description;
    private Double price;
    private Long sellerId;
    private Long categoryId;
    private Integer inventoryCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private static final Logger logger = LoggerFactory.getLogger(Product.class);

    @AggregateIdentifier
    private UUID productId;


    protected  Product() {
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
        AggregateLifecycle.apply(new  UpdateProductCommand(
                productId,
                command.getName(),
                command.getDescription(),
                command.getPrice(),
                command.getUpdatedAt()
        ));
    }

    @CommandHandler
    public void handle(DeleteProductCommand command) {
        AggregateLifecycle.apply(new DeleteProductCommand(
                productId,
                command.getDeletedAt()
        ));
    }

    @CommandHandler
    public void handle(UpdateProductInventoryCommand command) {
        AggregateLifecycle.apply(new UpdateProductInventoryCommand(
                productId,
                command.getInventoryCount(),
                command.getCategoryId(),
                command.getUpdatedAt()));
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
        AggregateLifecycle.apply(new ProductUpdatedEvent(
          event.getProductId(),
                event.getName(),
                event.getDescription(),
                event.getCategoryId(),
                event.getPrice(),
                event.getUpdatedAt()
        ));

    }

    @EventSourcingHandler
    public void on(ProductDeletedEvent event) {
        AggregateLifecycle.markDeleted();
    }

    @EventSourcingHandler
    public void on(ProductInventoryUpdatedEvent event) {
        this.inventoryCount = event.getInventoryCount();
    }

}
