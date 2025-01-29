package com.order.service.gui.order.dto;

import java.util.List;
import java.util.UUID;

public record CreateOrderDTO(List<UUID> products, Long paymentMethod, Long buyerId) { }
