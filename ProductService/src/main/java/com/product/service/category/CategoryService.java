package com.product.service.category;

import com.product.service.category.dto.CreateCategoryDTO;
import com.product.service.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;
    public Category createCategory(CreateCategoryDTO createCategoryDTO){

        Category category = new Category();
        category.setName(createCategoryDTO.name());
        category.setDescription(createCategoryDTO.description());

        return categoryRepository.save(category);
    }

    public Category findById(Long categoryId){
        return categoryRepository.findById(categoryId).orElseThrow(()-> new NotFoundException("category", categoryId));
    }

}
