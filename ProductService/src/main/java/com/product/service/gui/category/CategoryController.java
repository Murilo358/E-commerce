package com.product.service.gui.category;

import com.product.service.coreapi.commands.category.CreateCategoryCommand;
import com.product.service.coreapi.commands.category.DeleteCategoryCommand;
import com.product.service.coreapi.commands.category.UpdateCategoryCommand;
import com.product.service.coreapi.queries.category.FindAllCategoriesQuery;
import com.product.service.coreapi.queries.category.FindCategoryByNameQuery;
import com.product.service.coreapi.queries.category.FindCategoryQuery;
import com.product.service.gui.category.dto.CreateCategoryDTO;
import com.product.service.gui.category.dto.UpdateCategoryDTO;
import com.product.service.query.category.CategoryView;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("category")
public class CategoryController {


    @Autowired
    CommandGateway commandGateway;

    @Autowired
    private QueryGateway queryGateway;

    @PostMapping("/create")
    public CompletableFuture<UUID> createCategory(@RequestBody CreateCategoryDTO createCategoryDTO){
        return commandGateway.send(
            CreateCategoryCommand.builder()
                    .id(UUID.randomUUID())
                    .name(createCategoryDTO.name())
                    .description(createCategoryDTO.description())
                    .createdAt(LocalDateTime.now())
                    .build()
        );
    }


    @PutMapping("/update/{categoryId}")
    public CompletableFuture<UUID> updateCategory(@PathVariable  String categoryId, @RequestBody UpdateCategoryDTO updateCategoryDTO){
        return commandGateway.send(
                UpdateCategoryCommand.builder()
                        .id(UUID.fromString(categoryId))
                        .name(updateCategoryDTO.name())
                        .description(updateCategoryDTO.description())
                       .updatedAT(LocalDateTime.now()).build()
        );
    }

    @DeleteMapping("/delete/{categoryId}")
    public CompletableFuture<UUID> deleteCategory(@PathVariable UUID categoryId){
        return commandGateway.send(
                DeleteCategoryCommand.builder()
                        .id(categoryId)
                        .deletedAt(LocalDateTime.now())
        );
    }

    @GetMapping("/getByName/{categoryName}")
    public CompletableFuture<List<CategoryView>> getByName(@PathVariable String categoryName){
        return queryGateway.query(
                new FindCategoryByNameQuery(categoryName),
                ResponseTypes.multipleInstancesOf(CategoryView.class)
        );
    }
    @GetMapping("/get/{categoryId}")
    public CompletableFuture<CategoryView> getById(@PathVariable UUID categoryId){
        return queryGateway.query(
                new FindCategoryQuery(categoryId),
                ResponseTypes.instanceOf(CategoryView.class)
        );
    }

    @GetMapping("/getAll")
    public CompletableFuture<List<CategoryView>> getAll(
            @RequestParam(name = "startPage",defaultValue = "0") Integer startPage,
            @RequestParam(name = "endPage",defaultValue = "50") Integer endPage){

        return queryGateway.query(
                new FindAllCategoriesQuery(startPage, endPage),
                ResponseTypes.multipleInstancesOf(CategoryView.class)
        );
    }


}
