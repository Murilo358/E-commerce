package com.product.service.query;


import com.product.service.coreapi.events.ProductCreatedEvent;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductProjector {

    @Autowired
    ProductRepository productRepository;

    @EventHandler
    public void on(ProductCreatedEvent event){
        ProductView productView = ProductView
                .builder()
                .id(event.getProductId().toString())
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
}
