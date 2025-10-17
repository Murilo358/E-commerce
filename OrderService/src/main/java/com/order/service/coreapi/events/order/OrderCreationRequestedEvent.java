package com.order.service.coreapi.events.order;

import com.order.service.gui.order.dto.OrderProductDTO;
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
public class OrderCreationRequestedEvent {

    @RoutingKey
    private UUID orderId;
    private List<OrderProductDTO> products;
    private UUID shippingInformationId;
    private OrderStatus orderStatus;
    private Long paymentMethod;
    private Long buyerid;

}
