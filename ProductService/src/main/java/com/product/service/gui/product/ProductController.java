package com.product.service.gui.product;

import com.product.service.wrappers.PageableWrapper;
import com.product.service.coreapi.commands.product.CreateProductCommand;
import com.product.service.coreapi.commands.product.DeleteProductCommand;
import com.product.service.coreapi.commands.product.UpdateProductCommand;
import com.product.service.coreapi.queries.product.FindAllProductsQuery;
import com.product.service.coreapi.queries.product.FindProductQuery;
import com.product.service.gui.product.dto.CreateProductCommandDTO;
import com.product.service.gui.product.dto.UpdateProductCommandDTO;
import com.product.service.query.product.ProductView;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("products")
public class ProductController {

    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;

    public ProductController(CommandGateway commandGateway, QueryGateway queryGateway) {
        this.commandGateway = commandGateway;
        this.queryGateway = queryGateway;
    }

    @PostMapping("/create")
    public CompletableFuture<UUID> createProduct(@RequestBody CreateProductCommandDTO createProductCommandDTO) {

        return commandGateway.send(
                CreateProductCommand
                        .builder()
                        .productId(UUID.randomUUID())
                        .name(createProductCommandDTO.name())
                        .description(createProductCommandDTO.description())
                        .price(createProductCommandDTO.price())
                        .sellerId(createProductCommandDTO.sellerId())
                        .categoryId(createProductCommandDTO.categoryId())
                        .inventoryCount(createProductCommandDTO.inventoryCount())
                        .createdAt(LocalDateTime.now())
                        .updatedAt(null).build()
        );
    }

    @PostMapping("/update/{productId}")
    public CompletableFuture<UUID> updateProduct(@PathVariable("productId") UUID productId, @RequestBody UpdateProductCommandDTO updateProductCommand) {


        return commandGateway.send(
                UpdateProductCommand
                        .builder()
                        .productId(productId)
                        .name(updateProductCommand.name())
                        .description(updateProductCommand.description())
                        .categoryId(updateProductCommand.categoryId())
                        .price(updateProductCommand.price())
                        .updatedAt(LocalDateTime.now())
                        .build()
        );
    }

    @DeleteMapping("/delete/{productId}")
    public CompletableFuture<UUID> DeleteProductCommand(@PathVariable("productId") UUID productId) {


        return commandGateway.send(
                DeleteProductCommand
                        .builder()
                        .productId(productId)
                        .deletedAt(LocalDateTime.now()).build()
        );
    }

    @GetMapping("/get/{productId}")
    public CompletableFuture<ProductView> findProduct(@PathVariable("productId") UUID productId) {
        return queryGateway.query(
                new FindProductQuery(productId),
                ResponseTypes.instanceOf(ProductView.class)
        );
    }

    @GetMapping("/getAll")
    public CompletableFuture<List<ProductView>> getAllProducts(@PageableDefault(page = 0,  size = 20, direction = Sort.Direction.DESC) Pageable pageable){

        return queryGateway.query(
                new FindAllProductsQuery(PageableWrapper.fromPageable(pageable)),
                ResponseTypes.multipleInstancesOf(ProductView.class)
        );

    }


}
