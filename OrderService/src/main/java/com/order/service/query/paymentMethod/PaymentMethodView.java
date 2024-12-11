package com.order.service.query.paymentMethod;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="payment_methods", schema="users_management")
public class PaymentMethodView {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @Column
    private Long paymentTypeId;

    @Column
    private Long userId;

    @Column
    private boolean isDefault;
}
