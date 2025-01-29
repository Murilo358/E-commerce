package com.order.service.dto.seller;


public record SellerDto(Long sellerId,
                        String name,
                        Integer newProductsLastMonth,
                        Integer newSalesLastMonth) {
}
