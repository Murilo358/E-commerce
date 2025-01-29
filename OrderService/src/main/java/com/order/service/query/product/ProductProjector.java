package com.order.service.query.product;


import com.order.service.coreapi.queries.product.FindProductsByIdsQuery;
import com.order.service.dto.category.CategoryDto;
import com.order.service.dto.productDetail.OrderProduct;
import com.order.service.dto.seller.SellerDto;
import com.order.service.query.category.CategoryRepository;
import com.order.service.query.category.CategoryView;
import com.order.service.query.user.UserRepository;
import com.order.service.query.user.UserView;
import com.order.service.utils.I18nUtils;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


@ProcessingGroup("events")
@Component
public class ProductProjector {


    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    private final UserRepository userRepository;

    public ProductProjector(ProductRepository productRepository, CategoryRepository categoryRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

//    private final KafkaTemplate<String, Object> kafkaTemplate;
//
//    private final KafkaPublisher kafkaPublisher;
//
//    public ProductProjector(ProductRepository productRepository, CategoryRepository categoryRepository, UserRepository userRepository, KafkaTemplate<String, Object> kafkaTemplate, KafkaPublisher kafkaPublisher, Product product) {
//        this.productRepository = productRepository;
//        this.categoryRepository = categoryRepository;
//        this.userRepository = userRepository;
//        this.kafkaTemplate = kafkaTemplate;
//        this.kafkaPublisher = kafkaPublisher;
//    }

    @QueryHandler
    public List<OrderProduct> handle(FindProductsByIdsQuery query) {

        return productRepository.findAllById(query.getProductIds()).stream().filter(Objects::nonNull).map(this::buildProductDto).toList();
    }

    public OrderProduct buildProductDto(ProductView productView) {
        return buildProductDto(Optional.ofNullable(productView));
    }

    public OrderProduct buildProductDto(Optional<ProductView> productView) {

        OrderProduct orderProduct = new OrderProduct();

        if (productView.isPresent()) {

            ProductView product = productView.get();

            orderProduct.setId(product.getId());
            orderProduct.setName(product.getName());
            orderProduct.setDescription(product.getDescription());
            orderProduct.setPrice(product.getPrice());
            orderProduct.setInventoryCount(product.getInventoryCount());
            orderProduct.setUpdatedAt(product.getUpdatedAt());
            orderProduct.setCreatedAt(product.getCreatedAt());
            //TODO implement it
            orderProduct.setSoldLastMonthCount(100);

            Optional<CategoryView> categoryOpt = categoryRepository.findById(product.getCategoryId());

            if (categoryOpt.isPresent()) {

                CategoryView category = categoryOpt.get();
                CategoryDto categoryDto = new CategoryDto(product.getCategoryId(), I18nUtils.getI18nValue(category.getName()), I18nUtils.getI18nValue(category.getDescription()));
                orderProduct.setCategory(categoryDto);

            }

            Optional<UserView> userOpt = userRepository.findById(product.getSellerId());

            if (userOpt.isPresent()) {

                UserView user = userOpt.get();
                SellerDto sellerDto = new SellerDto(1L, user.getName(), 10, 10);
                orderProduct.setSeller(sellerDto);

            }


        }

        return orderProduct;

    }


}
