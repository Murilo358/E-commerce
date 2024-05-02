package com.ecommerceCatalog.CommerceCatalog.product;

import com.ecommerceCatalog.CommerceCatalog.product.dto.CreateProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("products")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping("/create")
    public ResponseEntity<Product> createProduct(@RequestBody CreateProductDTO createProductDTO) {
        Product createdProduct = productService.createProduct(createProductDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }



}
