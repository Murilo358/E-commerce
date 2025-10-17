package com.order.service.query.order;

import com.fasterxml.jackson.databind.JsonNode;
import com.order.service.coreapi.events.order.OrderStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "orders", schema = "transactions")
@Getter
@Setter
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

    private Double totalPrice;

    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt;

    @Column(name = "updated_at")
    private LocalDate updatedAt = LocalDate.now();

    @Column(name = "buyer_id", nullable = false)
    private Long buyerId;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.PENDING;

    @Column(name = "shipping_info", nullable = false, columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private JsonNode shippingInfo;

    @Column(name = "canceled_at")
    private LocalDate canceledAt;

    @Column(name = "shipped_at")
    private LocalDate shippedAt;

    @Column(name = "delivered_at")
    private LocalDate deliveredAt;

    @Column(name = "approved_at")
    private LocalDate approvedAt;

    @Column(name = "weight")
    private Double weight;

}
