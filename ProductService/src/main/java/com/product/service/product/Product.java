package com.product.service.product;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="products", schema="catalog")
@SecondaryTable(name = "gateway.user", pkJoinColumns = @PrimaryKeyJoinColumn(name = "id"))
public class Product {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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
