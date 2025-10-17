package com.order.service.coreapi.commands.order;

import com.order.service.coreapi.events.order.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateOrderStateCommand {
    UUID orderId;
    OrderStatus orderStatus;
}
