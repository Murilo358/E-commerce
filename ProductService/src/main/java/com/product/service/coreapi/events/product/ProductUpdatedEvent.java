package com.product.service.coreapi.events.product;

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
public class ProductUpdatedEvent {
    UUID productId;
    private String name;
    private String description;
    private UUID categoryId;
    private Double price;
    private LocalDateTime updatedAt;
}


