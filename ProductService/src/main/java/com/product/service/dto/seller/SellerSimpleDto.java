package com.product.service.dto.seller;

public record SellerSimpleDto(
        Long id,
        String name,
        Integer newProductsLastMonth,
        Integer newSalesLastMonth
) {
}
