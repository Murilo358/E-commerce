package com.product.service.gui;

public record CreateProductCommandDTO(
        String name,
        String description,
        Double price,
        Long sellerId,
        Long categoryId,
        Integer inventoryCount
) {
}
