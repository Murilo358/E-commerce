package com.commerceOrderService.CommerceOrderService;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListeners {

    @KafkaListener(topics = "orders", groupId = "groupId" )
    void listener(OrderEvent data) {
        System.out.println("ProductName: " + data.getProductName() + " buyedQuantity: " + data.getBuyedQuantity());
    }
}
