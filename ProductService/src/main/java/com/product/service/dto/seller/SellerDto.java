package com.product.service.dto.seller;

import com.product.service.query.product.ProductView;
import org.springframework.data.domain.Page;

import java.util.Map;

public record SellerDto(
        String sellerName,
        Map<String, Page<ProductView>> sellersProducts
) {
}
