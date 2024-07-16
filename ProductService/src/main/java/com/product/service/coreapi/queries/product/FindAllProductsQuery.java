package com.product.service.coreapi.queries.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class FindAllProductsQuery {

    Integer min;
    Integer max;

}
