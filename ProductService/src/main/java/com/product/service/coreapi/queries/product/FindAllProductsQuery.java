package com.product.service.coreapi.queries.product;

import com.product.service.wrappers.PageableWrapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FindAllProductsQuery {

    PageableWrapper pageable;

}
