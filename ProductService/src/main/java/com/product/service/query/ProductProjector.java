package com.product.service.query;


import com.product.service.coreapi.events.product.ProductCreatedEvent;
import com.product.service.coreapi.events.product.ProductDeletedEvent;
import com.product.service.coreapi.events.product.ProductInventoryUpdatedEvent;
import com.product.service.coreapi.events.product.ProductUpdatedEvent;
import com.product.service.coreapi.queries.FindProductQuery;
import com.product.service.exception.NotFoundException;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


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
}
