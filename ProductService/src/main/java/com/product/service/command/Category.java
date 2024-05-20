package com.product.service.command;


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

import java.time.LocalDateTime;
import java.util.UUID;



public class Category {

    @AggregateIdentifier
    private UUID id;
    private String name;
    private String description;
    private Boolean systemDefault;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    public Category() {
    }

    @CommandHandler
    public Category(CreateCategoryCommand command) {
        AggregateLifecycle.apply(
                CategoryCreatedEvent.builder()
                        .id(command.getId())
                        .name(command.getName())
                        .description(command.getDescription())
                        .createdAt(command.getCreatedAt())
                        .build()
        );
    }


    @CommandHandler
    public void handle(UpdateCategoryCommand command) {
        AggregateLifecycle.apply(
                CategoryUpdatedEvent.builder()
                        .id(id)
                        .name(command.getName())
                        .description(command.getDescription())
                        .updatedAT(command.getUpdatedAT())
                        .build()
        );

    }

    @CommandHandler
    public void handle(DeleteCategoryCommand command) {
        AggregateLifecycle.apply(
                CategoryDeletedEvent.builder()
                        .id(id)
                        .deletedAt(command.getDeletedAt())
        );
    }


    @EventSourcingHandler
    public void on(CategoryCreatedEvent event) {
        this.id = event.getId();
        this.name = event.getName();
        this.description = event.getDescription();
        this.createdAt = event.getCreatedAt();
    }

    @EventSourcingHandler
    public void on(CategoryUpdatedEvent event) {
        this.id = event.getId();
        this.name = event.getName();
        this.description = event.getDescription();
        this.updatedAt = event.getUpdatedAT();
    }

    @EventSourcingHandler
    public void on(CategoryDeletedEvent event) {
        this.deletedAt = event.getDeletedAt();
        AggregateLifecycle.markDeleted();
    }


}
