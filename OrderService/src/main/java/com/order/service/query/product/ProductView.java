package com.order.service.query.product;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;


@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Getter
@Setter
@Table(name="products", schema="catalog")
public class ProductView {

    @Id
    private UUID id;
    @Column
    private String name;
    @Column
    private String description;
    @Column
    private Double price;

    @JoinColumn(name = "seller_id", table = "gateway.user")
    private Long sellerId;

    @JoinColumn(name = "category_id", table = "categories", referencedColumnName ="id")
    private UUID categoryId;

    @Column
    private Integer inventoryCount;
    @Column
    private LocalDateTime createdAt;
    @Column
    private LocalDateTime updatedAt;

    @Column
    private Double weight;


}
