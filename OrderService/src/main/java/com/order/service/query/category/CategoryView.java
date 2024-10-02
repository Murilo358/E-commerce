package com.order.service.query.category;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
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

    @Column(nullable = false, name="systemdefault")
    public boolean systemDefault;

    @Column(nullable = false, name="created_at")
    public LocalDateTime createdAt;

    @Column( name="updated_at")
    public LocalDateTime updatedAt;

}