package com.order.service.gui.order.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;

import java.util.List;
import java.util.UUID;

public record CreateOrderDTO(@NotEmpty List<OrderProductDTO> products, @NonNull Long paymentMethodId, @NotNull UUID shippingInformationId, @NonNull Long buyerId) { }
