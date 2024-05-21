package com.product.service.gui.promotion;

import com.product.service.coreapi.commands.promotion.CreatePromotionCommand;
import com.product.service.gui.promotion.dto.CreatePromotionDTO;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/promotion")
public class PromotionController
{

    @Autowired
    CommandGateway commandGateway;

    @PostMapping("/create")
    public CompletableFuture<UUID> createPromotion(@RequestBody CreatePromotionDTO createPromotionDTO){
        return commandGateway.send(
                CreatePromotionCommand.builder()
                        .productId(createPromotionDTO.productId())
                        .description(createPromotionDTO.description())
                        .startDate(createPromotionDTO.startDate())
                        .endDate(createPromotionDTO.endDate()).build()
        );
    }

}
