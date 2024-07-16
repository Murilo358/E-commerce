package com.product.service.coreapi.queries.category;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;

@Data
@Builder
@AllArgsConstructor
public class FindAllCategoriesQuery {

    Integer min;
    Integer max;
}
