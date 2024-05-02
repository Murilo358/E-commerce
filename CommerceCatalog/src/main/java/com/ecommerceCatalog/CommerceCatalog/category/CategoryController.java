package com.ecommerceCatalog.CommerceCatalog.category;

import com.ecommerceCatalog.CommerceCatalog.category.dto.CreateCategoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;


    @PostMapping("/create")
    public ResponseEntity<Category> createCategory(@RequestBody CreateCategoryDTO createCategoryDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.createCategory(createCategoryDTO));
    }
}
