package com.order.service.coreapi.events.order;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApproveOrderCommand {
    @TargetAggregateIdentifier
    private UUID orderId;
    private OrderStatus orderStatus;
    private String notes;
    private List<OrderProductState> products;
    private Object shippingInformation;
    private double totalPrice;
    private double totalWeight;
    private Object paymentMethod;
    private Long buyerid;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private LocalDate canceledAt;
    private LocalDate shippedAt;
    private LocalDate deliveredAt;
    private LocalDate approvedAt;


}
