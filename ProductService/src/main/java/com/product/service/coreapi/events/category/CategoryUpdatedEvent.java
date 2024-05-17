package com.product.service.coreapi.events.category;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryUpdatedEvent {

    private UUID id;
    private String name;
    private String description;
    private LocalDateTime updatedAT;

}
