package com.product.service.command;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.EntityId;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    @EntityId
    private UUID id;
    private String name;
    private String description;

}
