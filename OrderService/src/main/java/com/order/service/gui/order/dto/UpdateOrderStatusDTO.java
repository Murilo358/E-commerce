package com.order.service.gui.order.dto;


import com.order.service.coreapi.events.order.OrderStatus;

import java.util.UUID;

public record UpdateOrderStatusDTO(UUID orderId, OrderStatus status) {
}
