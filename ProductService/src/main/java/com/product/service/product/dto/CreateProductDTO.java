package com.product.service.product.dto;

public record CreateProductDTO(String name, String description, Double price, Long sellerId, Long categoryId, Integer inventoryCount) {
}
