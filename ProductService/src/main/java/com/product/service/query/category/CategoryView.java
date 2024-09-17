package com.product.service.query.category;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;




@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "categories", schema = "catalog")
public class CategoryView {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public UUID id;

    @Column(nullable = false)
    public String name;

    @Column(nullable = false)
    public String description;

    @Column(nullable = false)
    public boolean systemDefault;
}
