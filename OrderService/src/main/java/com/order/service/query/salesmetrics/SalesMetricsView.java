package com.order.service.query.salesmetrics;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "sales_metrics")
public class SalesMetricsView {

    @Id
    @Column(name = "product_id")
    private UUID productId;
    @Column(name = "total_sold")
    private long totalSold;
    @Column(name = "last_sold")
    private OffsetDateTime lastSold;
    @Column(name = "sold_last_month")
    private long soldLastMonth;

}
