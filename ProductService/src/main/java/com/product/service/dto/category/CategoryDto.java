package com.product.service.dto.category;


import java.util.UUID;

public record CategoryDto(UUID id, String name, String description) {
}
