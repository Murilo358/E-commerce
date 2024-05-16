package com.product.service.product;


import com.product.service.category.CategoryService;
import com.product.service.product.dto.CreateProductDTO;
import com.product.service.query.ProductRepository;
import com.product.service.query.ProductView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    CategoryService categoryService;

    @Autowired
    ProductRepository productRepository;


    public ProductView createProduct(CreateProductDTO createProductDTO){

        categoryService.findById(createProductDTO.categoryId());

        ProductView productView = new ProductView();
        productView.setCategoryId(createProductDTO.categoryId());
        productView.setName(createProductDTO.name());
        productView.setCreatedAt(LocalDateTime.now());
        productView.setSellerId(createProductDTO.sellerId());
        productView.setDescription(createProductDTO.description());
        productView.setPrice(createProductDTO.price());
        productView.setInventoryCount(createProductDTO.inventoryCount());

        return productRepository.save(productView);

    }

    public List<ProductView> getAllProducts() {
        return productRepository.findAll();
    }
}
