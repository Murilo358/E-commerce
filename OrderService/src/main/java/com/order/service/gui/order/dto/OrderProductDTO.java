package com.order.service.gui.order.dto;


import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record OrderProductDTO(@NotNull UUID productId, @NotNull Long quantity) {
}
