package com.product.service.gui.promotion.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record CreatePromotionDTO(UUID productId, String description, LocalDateTime startDate, LocalDateTime endDate) {
}
