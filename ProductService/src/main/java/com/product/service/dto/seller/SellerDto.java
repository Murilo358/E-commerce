package com.product.service.dto.seller;

import com.product.service.query.product.ProductView;

import java.util.List;
import java.util.Map;

public record SellerDto(
        String sellerName,
        Map<String, List<ProductView>> sellersProducts
) {
}
