package com.product.service.coreapi.queries.category;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FindCategoryByNameQuery {

    public String categoryName;
}
