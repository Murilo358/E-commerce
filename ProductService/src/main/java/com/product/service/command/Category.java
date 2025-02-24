package com.product.service.command;


import com.product.service.adapters.DateTimeConversion;
import com.product.service.coreapi.commands.category.CreateCategoryCommand;
import com.product.service.coreapi.commands.category.DeleteCategoryCommand;
import com.product.service.coreapi.commands.category.UpdateCategoryCommand;
import com.product.service.coreapi.events.category.CategoryCreatedEvent;
import com.product.service.coreapi.events.category.CategoryDeletedEvent;
import com.product.service.coreapi.events.category.CategoryUpdatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.UUID;


@Aggregate
public class Category {

    @AggregateIdentifier
    private UUID id;
    private String name;
    private String description;
    private Boolean systemDefault;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private OffsetDateTime deletedAt;

    public Category() {
    }

    @CommandHandler
    public Category(CreateCategoryCommand command) {
        CategoryCreatedEvent categoryCreatedEvent = CategoryCreatedEvent.newBuilder()
                .setId(command.getId())
                .setSystemDefault(command.isSystemDefault())
                .setName(command.getName())
                .setDescription(command.getDescription())
                .setCreatedAt(command.getCreatedAt().toInstant(ZoneOffset.UTC))
                .build();

        AggregateLifecycle.apply(categoryCreatedEvent);
    }


    @CommandHandler
    public void handle(UpdateCategoryCommand command) {
        AggregateLifecycle.apply(
                CategoryUpdatedEvent.newBuilder()
                        .setId(id)
                        .setName(command.getName())
                        .setDescription(command.getDescription())
                        .setUpdatedAt(command.getUpdatedAt().toInstant(ZoneOffset.UTC))
                        .build()
        );

    }

    @CommandHandler
    public void handle(DeleteCategoryCommand command) {
        AggregateLifecycle.apply(
                CategoryDeletedEvent.newBuilder()
                        .setId(id)
                        .setDeletedAt(command.getDeletedAt().toInstant(ZoneOffset.UTC))
        );
    }


    @EventSourcingHandler
    public void on(CategoryCreatedEvent event) {
        this.id = event.getId();
        this.name = event.getName();
        this.description = event.getDescription();
        this.createdAt = DateTimeConversion.fromInstant(event.getCreatedAt());
    }

    @EventSourcingHandler
    public void on(CategoryUpdatedEvent event) {
        this.id = event.getId();
        this.name = event.getName();
        this.description = event.getDescription();
        this.updatedAt = DateTimeConversion.fromInstant(event.getUpdatedAt());
    }

    @EventSourcingHandler
    public void on(CategoryDeletedEvent event) {
        this.deletedAt = DateTimeConversion.fromInstant(event.getDeletedAt());
        AggregateLifecycle.markDeleted();
    }


}
