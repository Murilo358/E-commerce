package com.order.service.coreapi.commands.order;

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
public class RequestOrderCreationCommand { //todo realmente sera a mesma coisa que o ordecreationRequestEvent

    @RoutingKey
    UUID orderId;
    List<OrderProductDTO> products;
    UUID shippingInformationId;
    Long paymentMethod;
    Long buyerid;
}
