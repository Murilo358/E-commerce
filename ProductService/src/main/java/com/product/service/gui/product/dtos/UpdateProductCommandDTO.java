package com.product.service.gui.product.dtos;

import java.util.UUID;

public record UpdateProductCommandDTO(
        String name,
        String description,
        UUID categoryId,
        Double price) {
}
