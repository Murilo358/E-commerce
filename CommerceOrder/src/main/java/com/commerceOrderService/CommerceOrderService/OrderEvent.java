package com.commerceOrderService.CommerceOrderService;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class OrderEvent {


    private String eventId;
    private String productName;
    private Integer buyedQuantity;
}
