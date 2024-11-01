package com.order.service.query.order;

import org.springframework.data.jpa.repository.JpaRepository;



public interface OrderRepository extends JpaRepository<OrderView, Long> {
}
