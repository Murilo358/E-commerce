package com.product.service.query.paymentMethod;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@Table(name = "payment_methods", schema = "users_management")
public class PaymentMethodView {
    @Id
    private UUID id;

    @Column
    private Long paymentTypeId;

    @Column
    private Long userId;

    @Column
    private boolean isDefault;
}
