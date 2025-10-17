package com.order.service.gui.order;


import com.order.service.coreapi.commands.order.RequestOrderCreationCommand;
import com.order.service.gui.order.dto.CreateOrderDTO;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final CommandGateway commandGateway;

    public OrderController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }


    @PostMapping("/create")
    public ResponseEntity<UUID> createOrder(@RequestBody CreateOrderDTO createOrderDTO) {

        UUID orderId = UUID.randomUUID();

        RequestOrderCreationCommand orderCreationCommand = RequestOrderCreationCommand
                .builder()
                .orderId(orderId)
                .buyerid(createOrderDTO.buyerId())
                .shippingInformationId(createOrderDTO.shippingInformationId())
                .paymentMethod(createOrderDTO.paymentMethodId())
                .products(createOrderDTO.products())
                .build();

        commandGateway.send(orderCreationCommand);

        return ResponseEntity.accepted().body(orderId);

    }


}
