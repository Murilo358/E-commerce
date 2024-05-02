package com.ecommerceCatalog.CommerceCatalog.category;

import com.ecommerceCatalog.CommerceCatalog.category.dto.CreateCategoryDTO;
import com.ecommerceCatalog.CommerceCatalog.exception.NotFoundException;
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
