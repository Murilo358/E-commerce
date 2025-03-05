package com.order.service.gui.order.dto;

import java.util.List;

public record CreateOrderDTO(List<OrderProductDTO> products, Long paymentMethod, Long buyerId) { }
