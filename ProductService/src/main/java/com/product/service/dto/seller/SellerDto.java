package com.product.service.dto.seller;

public record SellerDto(
        Long id,
        String name,
        Integer newProductsLastMonth,
        Integer newSalesLastMonth
) {
}
