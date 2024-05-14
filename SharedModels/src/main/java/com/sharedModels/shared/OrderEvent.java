package com.sharedModels.shared;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderEvent   {

    private String eventId;
    private String productName;
    private Integer buyedQuantity;

}
