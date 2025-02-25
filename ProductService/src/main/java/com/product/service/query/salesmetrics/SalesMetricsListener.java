package com.product.service.query.salesmetrics;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.product.service.adapters.DateTimeConversion;
import com.product.service.coreapi.events.order.OrderProductState;
import com.product.service.query.order.OrderView;
import jakarta.persistence.PostPersist;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

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

            for (OrderProductState item : orderProductStates) {

                Optional<SalesMetricsView> byId = salesMetricsRepository.findById(item.getProductId());
                // todo get item quantity instead of 1
                int totalSold = byId.map(i -> i.getTotalSold() + 1).orElse(1);

                SalesMetricsView salesMetrics = SalesMetricsView.builder()
                        .productId(item.getProductId())
                        .totalSold(totalSold)
                        .lastSold(DateTimeConversion.fromInstant(item.getCreatedAt())).build();

                salesMetricsRepository.save(salesMetrics);
            }

        } catch (Exception e) {
        }
    }
}
