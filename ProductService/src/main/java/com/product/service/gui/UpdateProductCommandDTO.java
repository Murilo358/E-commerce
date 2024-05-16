package com.product.service.gui;

public record UpdateProductCommandDTO(
        String productId,
        String name,
        String description,
        Double price) {
}
