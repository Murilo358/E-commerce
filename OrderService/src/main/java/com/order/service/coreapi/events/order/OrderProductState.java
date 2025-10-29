package com.order.service.coreapi.events.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderProductState {

    private UUID productId;
    private String name;
    private String description;
    private Double price;
    private Long  sellerId;
    private String sellersName;
    private UUID categoryId;
    private String categoryName;
    private Integer inventoryCount;
    private Long soldQuantity;

}
