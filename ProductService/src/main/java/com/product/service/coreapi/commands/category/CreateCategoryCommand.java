package com.product.service.coreapi.commands.category;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.RoutingKey;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateCategoryCommand {

    @RoutingKey
    private UUID id;
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private boolean systemDefault;

}
