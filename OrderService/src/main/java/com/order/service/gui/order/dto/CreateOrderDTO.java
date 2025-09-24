package com.order.service.gui.order.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.NonNull;

import java.util.List;

public record CreateOrderDTO(@NotEmpty List<OrderProductDTO> products, @NonNull  Long paymentMethodId, @NonNull Long buyerId) { }
