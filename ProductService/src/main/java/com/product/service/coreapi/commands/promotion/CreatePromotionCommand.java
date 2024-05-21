package com.product.service.coreapi.commands.promotion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.RoutingKey;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePromotionCommand {

    @RoutingKey
    private UUID id;
    private UUID productId;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
