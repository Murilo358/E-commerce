package com.product.service.eventModels;

import lombok.*;

@AllArgsConstructor
@Data
public class OrderEvent   {

    private String eventId;
    private String productName;
    private Integer buyedQuantity;


}
