package com.product.service.query;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name="products", schema="catalog")
@SecondaryTable(name = "gateway.user", pkJoinColumns = @PrimaryKeyJoinColumn(name = "id"))
public class ProductView {

    @Id
    private String id;
    @Column
    private String name;
    @Column
    private String description;
    @Column
    private Double price;

    @JoinColumn(name = "seller_id", table = "gateway.user")
    private Long sellerId;

    @JoinColumn(name = "category_id", table = "categories", referencedColumnName ="id")
    private Long categoryId;

    @Column
    private Integer inventoryCount;
    @Column
    private LocalDateTime createdAt;
    @Column
    private LocalDateTime updatedAt;


}
