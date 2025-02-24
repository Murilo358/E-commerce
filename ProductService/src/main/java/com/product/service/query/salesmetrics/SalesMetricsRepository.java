package com.product.service.query.salesmetrics;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SalesMetricsRepository extends JpaRepository<SalesMetricsView, UUID>  {
}
