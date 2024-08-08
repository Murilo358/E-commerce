package com.product.service.dto;

import java.util.List;
import java.util.UUID;

public record CreateOrderDto(List<UUID> productsId, Long paymentMethod, Long userPaymentMethod) {
}
