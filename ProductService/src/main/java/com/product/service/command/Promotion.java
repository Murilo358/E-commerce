package com.product.service.command;

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
import java.util.UUID;

@Aggregate
public class Promotion {

    @AggregateIdentifier
    private UUID id;
    private UUID productId;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    protected Promotion(){}


    @CommandHandler
    public Promotion(CreatePromotionCommand command){
        AggregateLifecycle.apply(
                PromotionCreatedEvent.builder()
                        .id(command.getId())
                        .productId(command.getProductId())
                        .description(command.getDescription())
                        .startDate(command.getStartDate())
                        .endDate(command.getEndDate())
        );

    }


    @CommandHandler
    public void handle(DeletePromotionCommand command){
        AggregateLifecycle.apply(
                PromotionDeleteEvent.builder()
                        .promotionId(command.getPromotionId())
        );
    }

    @EventSourcingHandler
    public void on (PromotionCreatedEvent event){
        this.id = event.getId();
        this.productId = event.getProductId();
        this.description = event.getDescription();
        this.startDate = event.getStartDate();
        this.endDate = event.getEndDate();
    }

    @EventSourcingHandler
    public void on(PromotionDeleteEvent event){
        AggregateLifecycle.markDeleted();
    }


}
