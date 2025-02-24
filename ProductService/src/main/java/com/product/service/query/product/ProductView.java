package com.product.service.query.product;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
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

    @JoinColumn(name = "seller_id", table = "gateway.user")  //TODO SEARCH ABOUT IT TOO
    private Long sellerId;

    @JoinColumn(name = "category_id", table = "categories", referencedColumnName ="id") //TODO SEARCH ABOUT IT TOO
    private UUID categoryId;

    @Column
    private Integer inventoryCount;
    @Column
    private OffsetDateTime createdAt;
    @Column
    private OffsetDateTime updatedAt;


}
