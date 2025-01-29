package com.order.service.dto.productDetail;

import com.order.service.dto.category.CategoryDto;
import com.order.service.dto.seller.SellerDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderProduct {

    private UUID id;
    private String name;
    private String description;
    private Double price;
    private Double weight;
    private Integer soldLastMonthCount;
    private SellerDto seller;
    private CategoryDto category;
    private Integer inventoryCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
