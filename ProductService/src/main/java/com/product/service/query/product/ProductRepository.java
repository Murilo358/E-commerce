package com.product.service.query.product;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<ProductView, UUID> {
    @Transactional
    @Modifying
    @Query("update ProductView p set p.inventoryCount = ?1 where p.id = ?2")
    void updateInventoryCountById(@NonNull Integer inventoryCount, @NonNull UUID id);

    @Transactional
    @Modifying
    @Query("""
            update ProductView p set p.name = ?1, p.description = ?2, p.categoryId = ?3, p.price = ?4, p.updatedAt = ?5
            where p.id = ?6""")
    void updateNameAndDescriptionAndCategoryIdAndPriceAndUpdatedAtById(@NonNull String name, @NonNull String description, @NonNull UUID categoryId, @NonNull Double price, @NonNull LocalDateTime updatedAt, @NonNull UUID id);

    List<ProductView> findByCategoryId(@NonNull UUID categoryId, Pageable pageable);

    List<ProductView> findBySellerId(@NonNull Long sellerId, Pageable pageable);
}
