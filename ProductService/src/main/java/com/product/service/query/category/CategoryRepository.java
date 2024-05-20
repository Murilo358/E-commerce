package com.product.service.query.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryView, UUID> {
    @Transactional
    @Modifying
    @Query("update CategoryView c set c.name = ?1, c.description = ?2 where c.id = ?3")
    void updateNameAndDescriptionById(@NonNull String name, @NonNull String description, @NonNull UUID id);

    List<CategoryView> findByNameContains(@NonNull String name);
}
