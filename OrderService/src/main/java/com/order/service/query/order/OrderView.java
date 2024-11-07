package com.order.service.query.order;

import com.fasterxml.jackson.databind.JsonNode;
import com.order.service.coreapi.events.order.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;


import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "orders", schema = "transactions")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderView {

    @Id
    private UUID id;

    @Column(name = "products", nullable = false, columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private JsonNode products;

    @Column(name = "payment_method", nullable = false)
    private Long paymentMethod;

    @Column(name = "total_price", nullable = false, precision = 10, scale = 2)
    private Double totalPrice;

    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt;

    @Column(name = "updated_at")
    private LocalDate updatedAt = LocalDate.now();

    @Column(name = "buyer_id", nullable = false)
    private Long buyerId;

    @Column(nullable = false, length = 50)
    private OrderStatus status = OrderStatus.PENDING;

    @Column(nullable = false, precision = 10, scale = 2)
    private Double weight;
}
