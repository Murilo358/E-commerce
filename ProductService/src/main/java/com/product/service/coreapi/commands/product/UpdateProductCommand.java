package com.product.service.coreapi.commands.product;

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
public class UpdateProductCommand {
    @TargetAggregateIdentifier
    UUID productId;
    private String name;
    private String description;
    private Double price;
    private UUID categoryId;
    private LocalDateTime updatedAt;
}
