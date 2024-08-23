package com.product.service.query.category;

import com.product.service.coreapi.events.category.CategoryCreatedEvent;
import com.product.service.coreapi.events.category.CategoryDeletedEvent;
import com.product.service.coreapi.events.category.CategoryUpdatedEvent;
import com.product.service.coreapi.queries.category.FindAllCategoriesQuery;
import com.product.service.coreapi.queries.category.FindCategoryByNameQuery;
import com.product.service.coreapi.queries.category.FindCategoryQuery;
import com.product.service.exception.NotFoundException;
import com.product.service.kafka.KafkaPublisher;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;


@Component
public class CategoryProjector {

    final
    CategoryRepository categoryRepository;

    final
    KafkaPublisher kafkaPublisher;

    public CategoryProjector(CategoryRepository categoryRepository, KafkaPublisher kafkaPublisher) {
        this.categoryRepository = categoryRepository;
        this.kafkaPublisher = kafkaPublisher;
    }

    @EventHandler
    public void on(CategoryCreatedEvent event) {

        categoryRepository.save(
                CategoryView.builder()
                        .id(event.getId())
                        .name(event.getName())
                        .description(event.getDescription())
                        .build()
        );

        kafkaPublisher.send(String.valueOf(event.getId()), event);

    }

    @EventHandler
    public void on(CategoryUpdatedEvent event){

        categoryRepository.updateNameAndDescriptionById(
                event.getName(),
                event.getDescription(),
                event.getId()
        );
        kafkaPublisher.send(String.valueOf(event.getId()), event);
    }

    @EventHandler
    public void on(CategoryDeletedEvent event){
        categoryRepository.deleteById(event.getId());
        kafkaPublisher.send(String.valueOf(event.getId()), event);
    }

    @QueryHandler
    public CategoryView getBy(FindCategoryQuery query){
        return categoryRepository.findById(query.getCategoryId()).orElseThrow(() -> new NotFoundException("Category", query.getCategoryId() ));
    }

    @QueryHandler
    public List<CategoryView> getBy(FindAllCategoriesQuery query){

        PageRequest pageRequest = PageRequest.of(query.getMin(), Math.min(query.getMax(), 100));

        return categoryRepository.findAll(pageRequest).getContent();

    }

    @QueryHandler
    public List<CategoryView> getBy(FindCategoryByNameQuery query){
        return categoryRepository.findByNameContains(query.getCategoryName());
    }

}
