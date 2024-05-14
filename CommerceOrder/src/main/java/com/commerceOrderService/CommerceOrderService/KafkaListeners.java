package com.commerceOrderService.CommerceOrderService;

import com.commerceOrderService.CommerceOrderService.eventModels.OrderEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaListeners {


    @KafkaListener(topics = "orders",groupId = "groupId")
    public void consumeEvents(OrderEvent order) {

        System.out.println("consumer consume the events: " + order.toString());

    }

}
