package com.product.service.query.order;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;


public interface OrderRepository extends JpaRepository<OrderView, UUID> {
}
