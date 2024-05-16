package com.product.service.coreapi.events;

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
public class ProductInventoryUpdatedEvent {
    UUID productId;
    private Integer inventoryCount;
    private LocalDateTime updatedAt;
}