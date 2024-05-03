package com.ecommerceCatalog.CommerceCatalog.product;

import com.ecommerceCatalog.CommerceCatalog.category.CategoryService;
import com.ecommerceCatalog.CommerceCatalog.product.dto.CreateProductDTO;
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


    public Product createProduct(CreateProductDTO createProductDTO){

        categoryService.findById(createProductDTO.categoryId());

        Product product = new Product();
        product.setCategoryId(createProductDTO.categoryId());
        product.setName(createProductDTO.name());
        product.setCreatedAt(LocalDateTime.now());
        product.setSellerId(createProductDTO.sellerId());
        product.setDescription(createProductDTO.description());
        product.setPrice(createProductDTO.price());
        product.setInventoryCount(createProductDTO.inventoryCount());

        return productRepository.save(product);

    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}
