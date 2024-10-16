package com.order.service.gui.order.dto;

import com.order.service.query.product.ProductView;

import java.util.List;

public record CreateOrderDTO(List<ProductView> products, Long paymentMethod, Long buyerid) { }
