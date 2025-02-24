package com.product.service.query.salesmetrics;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.product.service.coreapi.events.order.OrderProductState;
import com.product.service.query.order.OrderView;
import jakarta.persistence.PostPersist;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.List;

@Component
public class SalesMetricsListener {

    private final ObjectMapper objectMapper = new ObjectMapper();


    private final SalesMetricsRepository salesMetricsRepository;

    public SalesMetricsListener(SalesMetricsRepository salesMetricsRepository) {
        this.salesMetricsRepository = salesMetricsRepository;
    }

    @PostPersist
    public void afterInsert(OrderView orderView) {
        try {

            List<OrderProductState> orderProductStates = objectMapper.readValue(orderView.getProducts().toString(), new TypeReference<List<OrderProductState>>() {
            });

            OffsetDateTime createdAt = orderView.getCreatedAt();

            for (OrderProductState item :orderProductStates) {
            //todo implemente
            }

        } catch (Exception e) {
        }
    }
}
