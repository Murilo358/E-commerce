package com.product.service.command;

import com.product.service.adapters.DateTimeConversion;
import com.product.service.coreapi.commands.promotion.CreatePromotionCommand;
import com.product.service.coreapi.commands.promotion.DeletePromotionCommand;
import com.product.service.coreapi.events.promotion.PromotionCreatedEvent;
import com.product.service.coreapi.events.promotion.PromotionDeleteEvent;
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
public class Promotion {

    @AggregateIdentifier
    private UUID id;
    private UUID productId;
    private String description;
    private OffsetDateTime startDate;
    private OffsetDateTime endDate;

    protected Promotion(){}


    @CommandHandler
    public Promotion(CreatePromotionCommand command){
        AggregateLifecycle.apply(
                PromotionCreatedEvent.newBuilder()
                        .setId(command.getId())
                        .setProductId(command.getProductId())
                        .setDescription(command.getDescription())
                        .setStartDate(command.getStartDate() != null ? command.getStartDate().toInstant(ZoneOffset.UTC) : null )
                        .setEndDate(command.getEndDate() != null ? command.getEndDate().toInstant(ZoneOffset.UTC) : null )
        );

    }


    @CommandHandler
    public void handle(DeletePromotionCommand command){
        AggregateLifecycle.apply(
                PromotionDeleteEvent.newBuilder()
                        .setId(command.getPromotionId())
        );
    }

    @EventSourcingHandler
    public void on (PromotionCreatedEvent event){
        this.id = event.getId();
        this.productId = event.getProductId();
        this.description = event.getDescription();
        this.startDate = DateTimeConversion.fromInstant(event.getStartDate());
        this.endDate = DateTimeConversion.fromInstant(event.getEndDate());
    }

    @EventSourcingHandler
    public void on(PromotionDeleteEvent event){
        AggregateLifecycle.markDeleted();
    }


}
