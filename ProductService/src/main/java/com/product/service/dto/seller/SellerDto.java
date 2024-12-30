package com.product.service.dto.seller;

public record SellerDto(String name,
                        Integer newProductsLastMonth,
                        Integer newSalesLastMonth) {
}
