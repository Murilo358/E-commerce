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
import com.product.service.dto.seller.SellerSimpleDto;
import com.product.service.enums.DefaultCategories;
import com.product.service.kafka.publisher.KafkaPublisher;
import com.product.service.coreapi.events.product.ProductInventoryUpdatedEvent;
import com.product.service.coreapi.queries.product.FindAllProductsQuery;
import com.product.service.coreapi.queries.product.FindProductQuery;
import com.product.service.exception.NotFoundException;
import com.product.service.query.category.CategoryRepository;
import com.product.service.query.category.CategoryView;
import com.product.service.query.order.OrderRepository;
import com.product.service.query.salesmetrics.SalesMetricsRepository;
import com.product.service.query.salesmetrics.SalesMetricsView;
import com.product.service.query.user.UserRepository;
import com.product.service.query.user.UserView;
import com.product.service.utils.I18nUtils;
import com.product.service.wrappers.SortWrapper;
import com.product.service.wrappers.PageableWrapper;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
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

    private final OrderRepository orderRepository;

    private final SalesMetricsRepository salesMetricsRepository;

    public ProductProjector(ProductRepository productRepository, CategoryRepository categoryRepository, UserRepository userRepository, KafkaTemplate<String, Object> kafkaTemplate, KafkaPublisher kafkaPublisher, Product product, OrderRepository orderRepository, SalesMetricsRepository salesMetricsRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaPublisher = kafkaPublisher;
        this.product = product;
        this.orderRepository = orderRepository;
        this.salesMetricsRepository = salesMetricsRepository;
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
        OffsetDateTime offSetDateTime = DateTimeConversion.fromInstant(event.getUpdatedAt());

        productRepository.findById(event.getProductId()).ifPresentOrElse(
                (product) ->
                {
                    productRepository.updateNameAndDescriptionAndCategoryIdAndPriceAndUpdatedAtById(
                            event.getName(),
                            event.getDescription(),
                            event.getCategoryId(),
                            event.getPrice(),
                            offSetDateTime != null ? offSetDateTime : OffsetDateTime.now(),
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
    public PageableWrapper handle(FindBySellerId query) {

        String sellerName = userRepository.findById(query.getSellerId()).map(UserView::getName).orElse(null);
        Page<ProductView> productViewBySellerId = productRepository.findProductViewBySellerId(
                query.getSellerId(), query.getPageable().toPageable()
        );


        Map<String, List<ProductView>> collect = productViewBySellerId.stream().collect(Collectors.groupingBy(i -> categoryRepository.findById(i.getCategoryId()).map((c) -> I18nUtils.getI18nValue(c.getName())).orElse("Desconhecido")));

        PageableWrapper productViewPageableWrapper = PageableWrapper.fromPage(productViewBySellerId);
        SellerDto sellerDto = new SellerDto(sellerName, collect);
        productViewPageableWrapper.setContent(sellerDto);


        return productViewPageableWrapper;
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
            Long soldLastMonth = salesMetricsRepository.findById(product.getId()).map(SalesMetricsView::getSoldLastMonth).orElse(0L);
            productDto.setSoldLastMonthCount(soldLastMonth);

            Optional<CategoryView> categoryOpt = categoryRepository.findById(product.getCategoryId());

            if (categoryOpt.isPresent()) {

                CategoryView category = categoryOpt.get();
                CategoryDto categoryDto = new CategoryDto(product.getCategoryId(), I18nUtils.getI18nValue(category.getName()), I18nUtils.getI18nValue(category.getDescription()));
                productDto.setCategory(categoryDto);

            }

            Optional<UserView> userOpt = userRepository.findById(product.getSellerId());

            if (userOpt.isPresent()) {

                UserView user = userOpt.get();
                long ordersLastMonth = orderRepository
                        .findCountBySellerIdAndCreatedAtBetween(user.getId(), OffsetDateTime.now().minusMonths(1L), OffsetDateTime.now());

                long productsLastMonth = productRepository.countBySellerIdAndCreatedAtBetween(user.getId(), OffsetDateTime.now().minusMonths(1L), OffsetDateTime.now());

                SellerSimpleDto sellerSimpleDto = new SellerSimpleDto(user.getId(), user.getName(), productsLastMonth, ordersLastMonth);
                productDto.setSeller(sellerSimpleDto);

            }


        }

        return productDto;

    }

}
