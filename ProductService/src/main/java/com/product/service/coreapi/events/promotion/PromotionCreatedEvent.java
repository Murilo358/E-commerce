package com.product.service.coreapi.events.promotion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PromotionCreatedEvent {

    private UUID id;
    private UUID productId;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
