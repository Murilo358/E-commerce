package com.product.service.query.product;


import com.product.service.coreapi.events.product.ProductCreatedEvent;
import com.product.service.coreapi.events.product.ProductDeletedEvent;
import com.product.service.coreapi.events.product.ProductInventoryUpdatedEvent;
import com.product.service.coreapi.events.product.ProductUpdatedEvent;
import com.product.service.coreapi.queries.product.FindAllProductsQuery;
import com.product.service.coreapi.queries.product.FindProductQuery;
import com.product.service.exception.NotFoundException;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class ProductProjector {

    @Autowired
    ProductRepository productRepository;

    @EventHandler
    public void on(ProductCreatedEvent event) {
        ProductView productView = ProductView
                .builder()
                .id(event.getProductId())
                .name(event.getName())
                .description(event.getDescription())
                .price(event.getPrice())
                .sellerId(event.getSellerId())
                .categoryId(event.getCategoryId())
                .inventoryCount(event.getInventoryCount())
                .updatedAt(event.getUpdatedAt())
                .createdAt(event.getCreatedAt()).build();

        productRepository.save(productView);
    }

    @EventHandler
    public void on(ProductDeletedEvent event) {
        productRepository.deleteById(event.getProductId());
    }

    @EventHandler
    public void on(ProductInventoryUpdatedEvent event) {
        productRepository.findById(event.getProductId())
                .ifPresentOrElse(
                        (product) ->
                                productRepository.updateInventoryCountById(event.getInventoryCount(), product.getId()),
                        () -> {
                            throw new NotFoundException("Product", event.getProductId());
                        }
                );
    }

    @EventHandler
    public void on(ProductUpdatedEvent event) {
        productRepository.findById(event.getProductId()).ifPresentOrElse(
                (product) ->
                        productRepository.updateNameAndDescriptionAndCategoryIdAndPriceAndUpdatedAtById(
                                event.getName(),
                                event.getDescription(),
                                event.getCategoryId(),
                                event.getPrice(),
                                event.getUpdatedAt(),
                                product.getId()
                        )
                , () -> {
                    throw new NotFoundException("Product", event.getProductId());
                }

        );

    }

    @QueryHandler
    public ProductView handle(FindProductQuery query){
        return productRepository.findById(query.getProductId()).orElseThrow(() -> new NotFoundException("Product", query.getProductId()));
    }

    //To share exceptional information with the recipient it is recommended to wrap the exception in a QueryExecutionException with provided details.
    //TODO CHANGE OFFSET PAGINATION TO SEEK PAGINATION
    @QueryHandler
    public List<ProductView> handle(FindAllProductsQuery query){

        PageRequest pageRequest = PageRequest.of(query.getMin(), Math.min(query.getMax(), 100));

        return productRepository.findAll(pageRequest).getContent();
    }
}
