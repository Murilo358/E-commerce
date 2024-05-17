package com.product.service.command;


import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.EntityId;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.UUID;


@NoArgsConstructor
@Aggregate
public class Category {

    @EntityId
    private UUID id;
    private String name;
    private String description;
    private Boolean systemDefault;

}
