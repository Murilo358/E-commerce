package com.product.service.dto.seller;

import com.product.service.query.product.ProductView;
import com.product.service.wrappers.PageableWrapper;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public record SellerDto(
        String sellerName,
        Map<String, List<ProductView>> sellersProducts
) {
}
