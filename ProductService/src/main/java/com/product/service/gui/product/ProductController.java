package com.product.service.gui.product;

import com.product.service.coreapi.commands.product.CreateProductCommand;
import com.product.service.coreapi.commands.product.DeleteProductCommand;
import com.product.service.coreapi.commands.product.UpdateProductCommand;
import com.product.service.coreapi.queries.FindProductQuery;
import com.product.service.gui.product.dtos.CreateProductCommandDTO;
import com.product.service.gui.product.dtos.UpdateProductCommandDTO;
import com.product.service.query.ProductView;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("products")
public class ProductController {

    @Autowired
    private CommandGateway commandGateway;
    @Autowired
    private QueryGateway queryGateway;

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

}
