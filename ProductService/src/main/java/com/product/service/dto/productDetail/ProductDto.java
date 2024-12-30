package com.product.service.dto.productDetail;

import com.product.service.dto.category.CategoryDto;
import com.product.service.dto.seller.SellerDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
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
    private SellerDto seller;
    private CategoryDto category;
    private Integer inventoryCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
