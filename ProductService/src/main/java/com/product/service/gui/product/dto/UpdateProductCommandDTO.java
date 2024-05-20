package com.product.service.gui.product.dto;

import java.util.UUID;

public record UpdateProductCommandDTO(
        String name,
        String description,
        UUID categoryId,
        Double price) {
}
