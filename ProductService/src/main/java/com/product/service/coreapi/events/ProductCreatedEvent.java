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
public class ProductCreatedEvent{
    UUID productId;
    private String name;
    private String description;
    private Double price;
    private Long sellerId;
    private Long categoryId;
    private Integer inventoryCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}