package com.product.service.dto;

import com.product.service.query.product.ProductView;

import java.util.List;
import java.util.Map;

public record HomePageProductsDto (Map<String, List<ProductView>> products) {
}
