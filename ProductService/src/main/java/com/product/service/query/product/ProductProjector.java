package com.product.service.query.product;


import com.product.service.adapters.DateTimeConversion;
import com.product.service.command.Product;
import com.product.service.coreapi.events.product.ProductCreatedEvent;
import com.product.service.coreapi.events.product.ProductDeletedEvent;
import com.product.service.coreapi.events.product.ProductUpdatedEvent;
import com.product.service.coreapi.queries.product.FindForHomePageQuery;
import com.product.service.dto.HomePageProductsDto;
import com.product.service.enums.DefaultCategories;
import com.product.service.kafka.KafkaPublisher;
import com.product.service.coreapi.events.product.ProductInventoryUpdatedEvent;
import com.product.service.coreapi.queries.product.FindAllProductsQuery;
import com.product.service.coreapi.queries.product.FindProductQuery;
import com.product.service.exception.NotFoundException;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;


@ProcessingGroup("events")
@Component
public class ProductProjector {

    ProductRepository productRepository;

    final
    KafkaTemplate<String, Object> kafkaTemplate;

    final
    KafkaPublisher kafkaPublisher;

    public ProductProjector(ProductRepository productRepository, KafkaTemplate<String, Object> kafkaTemplate, KafkaPublisher kafkaPublisher) {
        this.productRepository = productRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaPublisher = kafkaPublisher;
    }

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
                .updatedAt(DateTimeConversion.fromInstant(event.getUpdatedAt()))
                .createdAt(DateTimeConversion.fromInstant(event.getCreatedAt()))
                .build();


        //Verify if the user exists
        //verify if the category exists
        //etc and etc

        productRepository.save(productView);

        kafkaPublisher.send(String.valueOf(event.getProductId()), event);

    }

    @EventHandler
    public void on(ProductDeletedEvent event) {
        productRepository.deleteById(event.getProductId());
        kafkaPublisher.send(String.valueOf(event.getProductId()), event);
    }

    @EventHandler
    public void on(ProductInventoryUpdatedEvent event) {
        productRepository.findById(event.getProductId())
                .ifPresentOrElse(
                        (product) ->
                        {
                            productRepository.updateInventoryCountById(event.getInventoryCount(), product.getId());
                            kafkaPublisher.send(String.valueOf(event.getProductId()), event);
                        },
                        () -> {
                            throw new NotFoundException("Product", event.getProductId());
                        }
                );
    }

    @EventHandler
    public void on(ProductUpdatedEvent event) {
        LocalDateTime localDateTime = DateTimeConversion.fromInstant(event.getUpdatedAt());

        productRepository.findById(event.getProductId()).ifPresentOrElse(
                (product) ->
                {
                    productRepository.updateNameAndDescriptionAndCategoryIdAndPriceAndUpdatedAtById(
                            event.getName(),
                            event.getDescription(),
                            event.getCategoryId(),
                            event.getPrice(),
                            localDateTime != null ? localDateTime : LocalDateTime.now(),
                            product.getId()
                    );
                    kafkaPublisher.send(String.valueOf(event.getProductId()), event);
                }
                , () -> {
                    throw new NotFoundException("Product", event.getProductId());
                }

        );

    }

    @QueryHandler
    public ProductView handle(FindProductQuery query) {
        return productRepository.findById(query.getProductId()).orElseThrow(() -> new NotFoundException("Product", query.getProductId()));
    }

    //To share exceptional information with the recipient it is recommended to wrap the exception in a QueryExecutionException with provided details.
    //TODO CHANGE OFFSET PAGINATION TO SEEK PAGINATION
    @QueryHandler
    public List<ProductView> handle(FindAllProductsQuery query) {


        return productRepository.findAll(query.getPageable().toPageable()).getContent();
    }

    @QueryHandler
    public HomePageProductsDto handle(FindForHomePageQuery query) {

        List<UUID> list = Arrays.stream(DefaultCategories.values())
                .map(DefaultCategories::getId)
                .toList();

        Map<String, List<ProductView>> groupedProducts = new HashMap<>();

        for (UUID categoryId : list) {

            List<ProductView> products = productRepository.findByCategoryId(categoryId, query.getPageable().toPageable());

            String categoryName = DefaultCategories.getById(categoryId).getName();
            groupedProducts.put(categoryName, products);
        }

        return new HomePageProductsDto(groupedProducts);

    }
}
