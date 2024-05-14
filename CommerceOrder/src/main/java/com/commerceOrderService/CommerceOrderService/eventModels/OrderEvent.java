package com.commerceOrderService.CommerceOrderService.eventModels;

import lombok.*;

@Data
public class OrderEvent {

    private String eventId;
    private String productName;
    private Integer buyedQuantity;

}
