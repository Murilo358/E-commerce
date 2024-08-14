package com.product.service.order;


import com.product.service.avro.OrderCreated;
import com.product.service.avro.Product;
import com.product.service.coreapi.queries.product.FindProductQuery;
import com.product.service.dto.CreateOrderDto;
import com.product.service.query.product.ProductView;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("order")
public class OrderController {

    @Autowired
    KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    private QueryGateway queryGateway;

    @PostMapping("/create")
    public void createOrder(@RequestBody CreateOrderDto createOrderDto){

        List<ProductView> list = createOrderDto.productsId().stream().map(i -> {
            try {
                return queryGateway.query(
                        new FindProductQuery(i),
                        ResponseTypes.instanceOf(ProductView.class)
                ).get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }).toList();


        List<Product> products = list.stream().map(i -> {
            return Product
                    .newBuilder()
                    .setCreatedAt(i.getCreatedAt() != null ? i.getCreatedAt().toLocalDate() : null)
                    .setCategoryId(i.getCategoryId())
                    .setDescription(i.getDescription())
                    .setName(i.getName())
                    .setPrice(i.getPrice())
                    .setCreatedAt(i.getCreatedAt() != null ? i.getCreatedAt().toLocalDate() : null)
                    .setInventoryCount(i.getInventoryCount())
                    .setUpdatedAt(i.getUpdatedAt() != null ? i.getUpdatedAt().toLocalDate() : null)
                    .setSellerId(i.getSellerId())
                    .setProductId(i.getId()).build();
        }).toList();


        new OrderCreated(products, 1L, LocalDate.now());
        kafkaTemplate.send("orders", new OrderCreated(products, 1L, LocalDate.now()));
    }


}
