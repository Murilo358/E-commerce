package com.order.service.saga.order;

import com.order.service.coreapi.events.order.ApproveOrderCommand;
import com.order.service.coreapi.events.order.OrderCreationRequestedEvent;
import com.order.service.coreapi.events.order.OrderProductState;
import com.order.service.coreapi.queries.product.FindProductsByIdsQuery;
import com.order.service.dto.productDetail.OrderProduct;
import com.order.service.gui.order.dto.OrderProductDTO;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;


import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Saga
public class OrderCreationSaga {

    @Autowired
    private transient CommandGateway commandGateway;

    @Autowired
    private transient QueryGateway queryGateway;

    public OrderCreationSaga() {}

    @SagaEventHandler(associationProperty = "orderId")
    @StartSaga
    public void on(OrderCreationRequestedEvent event) {

        UUID orderId = event.getOrderId();
        List<UUID> productIds = event.getProducts().stream()
                .map(OrderProductDTO::productId)
                .toList();

        Map<UUID, Long> quantityByProductId = event.getProducts().stream().collect(Collectors.toMap(OrderProductDTO::productId, OrderProductDTO::quantity));

        queryGateway.query(
                new FindProductsByIdsQuery(productIds),
                ResponseTypes.multipleInstancesOf(OrderProduct.class)
        ).thenAccept(products -> {


            double totalPrice = products.stream()
                    .mapToDouble(i -> i.getPrice() * quantityByProductId.get(i.getId()))
                    .sum();

            double totalWeight = products.stream()
                    .filter(i -> i.getWeight() != null)
                    .mapToDouble(i -> i.getWeight() * quantityByProductId.get(i.getId()))
                    .sum();

            List<OrderProductState> orderProductStates = products.stream().map(p -> createProductOrderState(p, quantityByProductId.get(p.getId()))).toList();


            ApproveOrderCommand approveOrderCommand = ApproveOrderCommand
                    .builder()
                    .orderId(orderId)
                    .products(orderProductStates)
                    .buyerid(1L)
                    .shippingInformation(null)//todo create table and get it
                    .totalPrice(totalPrice)
                    .totalWeight(totalWeight)
                    .paymentMethod(event.getPaymentMethod()) //todo create table and get it
                    .buyerid(event.getBuyerid())
                    .canceledAt(null)
                    .shippedAt(null)//todo impement shipping
                    .deliveredAt(null)
                    .approvedAt(LocalDate.now())
                    .build();


            commandGateway.send(approveOrderCommand);
        });

    }

    private OrderProductState createProductOrderState(OrderProduct product, Long soldQuantity) {
        return OrderProductState.builder()
                .productId(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .sellerId(product.getSeller().sellerId())
                .sellersName("Testing")
                .categoryId(product.getCategory().id())
                .categoryName("Testing")
                .inventoryCount(product.getInventoryCount())
                .soldQuantity(soldQuantity)
                .build();
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void on(ApproveOrderCommand event) {
        SagaLifecycle.end();
    }

}
