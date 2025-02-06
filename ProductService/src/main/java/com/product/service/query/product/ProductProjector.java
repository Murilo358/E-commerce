package com.product.service.query.product;


import com.product.service.adapters.DateTimeConversion;
import com.product.service.command.Product;
import com.product.service.coreapi.events.product.ProductCreatedEvent;
import com.product.service.coreapi.events.product.ProductDeletedEvent;
import com.product.service.coreapi.events.product.ProductUpdatedEvent;
import com.product.service.coreapi.queries.product.FindBySellerId;
import com.product.service.coreapi.queries.product.FindForHomePageQuery;
import com.product.service.dto.HomePageProductsDto;
import com.product.service.dto.category.CategoryDto;
import com.product.service.dto.productDetail.ProductDto;
import com.product.service.dto.seller.SellerDto;
import com.product.service.enums.DefaultCategories;
import com.product.service.kafka.publisher.KafkaPublisher;
import com.product.service.coreapi.events.product.ProductInventoryUpdatedEvent;
import com.product.service.coreapi.queries.product.FindAllProductsQuery;
import com.product.service.coreapi.queries.product.FindProductQuery;
import com.product.service.exception.NotFoundException;
import com.product.service.query.category.CategoryRepository;
import com.product.service.query.category.CategoryView;
import com.product.service.query.user.UserRepository;
import com.product.service.query.user.UserView;
import com.product.service.utils.I18nUtils;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@Slf4j
@ProcessingGroup("events")
@Component
public class ProductProjector {


    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    private final UserRepository userRepository;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private final KafkaPublisher kafkaPublisher;
    private final Product product;

    public ProductProjector(ProductRepository productRepository, CategoryRepository categoryRepository, UserRepository userRepository, KafkaTemplate<String, Object> kafkaTemplate, KafkaPublisher kafkaPublisher, Product product) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaPublisher = kafkaPublisher;
        this.product = product;
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
    public ProductDto handle(FindProductQuery query) {

        ProductView product = productRepository.findById(query.getProductId()).orElseThrow(() -> new NotFoundException("Product", query.getProductId()));

        List<ProductView> relatedProducts = productRepository.findByCategoryId(product.getCategoryId(), Pageable.unpaged());

        ProductDto productDto = buildProductDto(Optional.of(product));
        productDto.setRelatedProducts(relatedProducts);
        return productDto;
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

    @QueryHandler
    public Map handle(FindBySellerId query) {

        List<ProductView> bySellerId = productRepository.findBySellerId(query.getSellerId(), query.getPageable().toPageable());
        return  bySellerId.stream().map(this::buildProductDto).collect(Collectors.groupingBy(i -> i.getCategory().name()));

    }

    public ProductDto buildProductDto(ProductView productView) {
        return buildProductDto(Optional.of(productView));
    }

    public ProductDto buildProductDto(Optional<ProductView> productView) {

        ProductDto productDto = new ProductDto();

        if (productView.isPresent()) {

            ProductView product = productView.get();

            productDto.setId(product.getId());
            productDto.setName(product.getName());
            productDto.setDescription(product.getDescription());
            productDto.setPrice(product.getPrice());
            productDto.setInventoryCount(product.getInventoryCount());
            productDto.setUpdatedAt(product.getUpdatedAt());
            productDto.setCreatedAt(product.getCreatedAt());
            //TODO implement it
            productDto.setSoldLastMonthCount(100);

            Optional<CategoryView> categoryOpt = categoryRepository.findById(product.getCategoryId());

            if (categoryOpt.isPresent()) {

                CategoryView category = categoryOpt.get();
                CategoryDto categoryDto = new CategoryDto(product.getCategoryId(), I18nUtils.getI18nValue(category.getName()), I18nUtils.getI18nValue(category.getDescription()));
                productDto.setCategory(categoryDto);

            }

            Optional<UserView> userOpt = userRepository.findById(product.getSellerId());

            if (userOpt.isPresent()) {

                //todo fazer a query buscando as coisas do usuario
                UserView user = userOpt.get();
                SellerDto sellerDto = new SellerDto(user.getId(), user.getName(), 10, 10);
                productDto.setSeller(sellerDto);

            }


        }

        return productDto;

    }

}
