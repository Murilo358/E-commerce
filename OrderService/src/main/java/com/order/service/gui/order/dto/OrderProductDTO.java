package com.order.service.gui.order.dto;

import java.util.UUID;

public record OrderProductDTO(UUID productId, long quantity) {
}
