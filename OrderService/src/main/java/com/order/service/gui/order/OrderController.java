package com.order.service.gui.order;

import com.order.service.coreapi.commands.CreateOrderCommand;
import com.order.service.coreapi.commands.UpdateOrderStateCommand;
import com.order.service.gui.order.dto.CreateOrderDTO;
import com.order.service.gui.order.dto.UpdateOrderStatusDTO;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;

    public OrderController(CommandGateway commandGateway, QueryGateway queryGateway) {
        this.commandGateway = commandGateway;
        this.queryGateway = queryGateway;
    }


    @PostMapping("/create")
    public CompletableFuture<UUID> createOrder(@RequestBody CreateOrderDTO createOrderDTO) {

        return commandGateway.send(
                CreateOrderCommand
                        .builder()
                        .orderId(UUID.randomUUID())
                        .buyerid(createOrderDTO.buyerid())
                        .paymentMethod(createOrderDTO.paymentMethod())
                        .products(createOrderDTO.products()).build()
        );

    }

    @PostMapping("/updateStatus")
    public CompletableFuture<Boolean> updateOrderStatus(@RequestBody UpdateOrderStatusDTO UpdateOrderStatusDTO){
        return commandGateway.send(
                UpdateOrderStateCommand
                        .builder()
                        .orderId(UpdateOrderStatusDTO.orderId())
                        .orderStatus(UpdateOrderStatusDTO.status())
        );
    }

}
