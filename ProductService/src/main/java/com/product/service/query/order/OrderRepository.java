package com.product.service.query.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;


public interface OrderRepository extends JpaRepository<OrderView, UUID> {

    @Query("select count(*) from OrderView o where o.sellerId = ?1 and o.createdAt between ?2 and ?3")
    long findCountBySellerIdAndCreatedAtBetween(Long sellerId, OffsetDateTime createdAtStart, OffsetDateTime createdAtEnd);
}
