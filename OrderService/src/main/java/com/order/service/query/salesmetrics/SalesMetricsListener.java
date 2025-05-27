package com.order.service.query.salesmetrics;//package com.product.service.query.salesmetrics;
//
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.product.service.coreapi.events.order.OrderProductState;
//import com.product.service.query.order.OrderView;
//import jakarta.persistence.PostPersist;
//import jakarta.transaction.Transactional;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Lazy;
//import org.springframework.stereotype.Component;
//
//import java.time.OffsetDateTime;
//import java.util.List;
//import java.util.Optional;
//
//@Slf4j
//@Component
//public class SalesMetricsListener {
//
//    private final ObjectMapper objectMapper = new ObjectMapper();
//
//
//    private final SalesMetricsRepository salesMetricsRepository;
//
//    @Autowired
//    public SalesMetricsListener(@Lazy SalesMetricsRepository salesMetricsRepository) {
//        this.salesMetricsRepository = salesMetricsRepository;
//    }
//
//    @PostPersist
//    @Transactional
//    public void afterInsert(OrderView orderView) {
//        try {
//            List<OrderProductState> orderProductStates = objectMapper.readValue(
//                    orderView.getProducts().toString(),
//                    new TypeReference<List<OrderProductState>>() {}
//            );
//
//            OffsetDateTime createdAt = orderView.getCreatedAt();
//
//            for (OrderProductState item : orderProductStates) {
//
//                long quantitySold = item.getQuantity();
//
//                Optional<SalesMetricsView> existingMetrics = salesMetricsRepository.findById(item.getProductId());
//
//                OffsetDateTime oneMonthAgo = createdAt.minusMonths(1);
//
//                SalesMetricsView salesMetrics = existingMetrics
//                        .map(metrics -> {
//                            long soldLastMonth = (metrics.getLastSold().isAfter(oneMonthAgo))
//                                    ? metrics.getSoldLastMonth() + quantitySold
//                                    : quantitySold;
//
//                            return SalesMetricsView.builder()
//                                    .totalSold(metrics.getTotalSold() + quantitySold)
//                                    .soldLastMonth(soldLastMonth)
//                                    .lastSold(createdAt)
//                                    .build();
//                        })
//                        .orElse(SalesMetricsView.builder()
//                                .productId(item.getProductId())
//                                .totalSold(quantitySold)
//                                .soldLastMonth(quantitySold)
//                                .lastSold(createdAt)
//                                .build());
//
//                salesMetricsRepository.save(salesMetrics);
//            }
//        } catch (Exception e) {
//            log.error("Error while creating orders metrics");
//        }
//    }
//}
