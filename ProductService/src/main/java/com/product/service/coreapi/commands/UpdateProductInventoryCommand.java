package com.product.service.coreapi.commands;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProductInventoryCommand {
    @TargetAggregateIdentifier
    private UUID productId;
    private Integer inventoryCount;
    private UUID categoryId;
    private LocalDateTime updatedAt;
}