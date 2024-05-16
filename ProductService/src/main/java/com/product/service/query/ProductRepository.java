package com.product.service.query;

import com.product.service.query.ProductView;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductView, Long> {
}
