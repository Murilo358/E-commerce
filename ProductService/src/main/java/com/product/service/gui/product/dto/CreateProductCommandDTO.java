package com.product.service.gui.product.dto;

import java.util.UUID;

public record CreateProductCommandDTO(
        String name,
        String description,
        Double price,
        Long sellerId,
        UUID categoryId,
        Integer inventoryCount
) {
}
