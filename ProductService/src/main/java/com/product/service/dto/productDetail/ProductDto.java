package com.product.service.dto.productDetail;

import com.product.service.dto.category.CategoryDto;
import com.product.service.dto.seller.SellerSimpleDto;
import com.product.service.query.product.ProductView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private UUID id;
    private String name;
    private String description;
    private Double price;
    private Integer soldLastMonthCount;
    private SellerSimpleDto seller;
    private CategoryDto category;
    private Integer inventoryCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private List<ProductView> relatedProducts;

    //TODO IMPLEMENT RELATED PRODUCTS
}
