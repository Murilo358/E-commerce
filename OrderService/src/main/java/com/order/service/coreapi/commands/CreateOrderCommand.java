package com.order.service.coreapi.commands;

import com.order.service.query.product.ProductView;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.RoutingKey;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderCommand {
    @RoutingKey
    UUID orderId;
    List<ProductView> products;
    Long paymentMethod;
    Long buyerid;
//    LocalDateTime createdAt;


}
