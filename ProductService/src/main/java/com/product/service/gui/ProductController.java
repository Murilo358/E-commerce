package com.product.service.gui;

import com.product.service.coreapi.commands.CreateProductCommand;
import com.product.service.coreapi.commands.DeleteProductCommand;
import com.product.service.coreapi.commands.UpdateProductCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
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

    @PostMapping("/update")
    public CompletableFuture<UUID> updateProduct(@RequestBody UpdateProductCommandDTO updateProductCommand) {


        return commandGateway.send(
                UpdateProductCommand
                        .builder()
                        .productId(UUID.fromString(updateProductCommand.productId()))
                        .name(updateProductCommand.name())
                        .description(updateProductCommand.description())
                        .price(updateProductCommand.price())
                        .updatedAt(LocalDateTime.now()).build()
        );
    }

    @DeleteMapping("/delete/{productId}")
    public CompletableFuture<UUID> DeleteProductCommand(@PathVariable("productId") String productId) {


        return commandGateway.send(
                DeleteProductCommand
                        .builder()
                        .productId(UUID.fromString(productId))
                        .deletedAt(LocalDateTime.now()).build()
        );
    }

    @GetMapping("/get/{productId}")
    public void findProduct(@PathVariable("productId") String productId) {
        //TODO IMPLEMENT QUERY
    }

}
