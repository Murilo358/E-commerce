package com.order.service.saga.order;

import com.order.service.coreapi.events.order.ApproveOrderEvent;
import com.order.service.coreapi.events.order.OrderCreationRequestedEvent;
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

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Saga
public class OrderCreationSaga {

    private final transient CommandGateway commandGateway;
    private final transient QueryGateway queryGateway;

    public OrderCreationSaga(CommandGateway commandGateway, QueryGateway queryGateway) {
        this.commandGateway = commandGateway;
        this.queryGateway = queryGateway;
    }

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


            ApproveOrderEvent approveOrderEvent = ApproveOrderEvent
                    .builder()
                    .orderId(orderId)
                    .products(products)
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



            commandGateway.send(approveOrderEvent);
        });

    }


    @SagaEventHandler(associationProperty = "orderId")
    public void on(ApproveOrderEvent event) {
        SagaLifecycle.end();
    }

}
