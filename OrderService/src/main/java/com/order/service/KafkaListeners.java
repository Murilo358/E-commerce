package com.order.service;


import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaListeners {


    @KafkaListener(topics = "orders", groupId = "groupId", autoStartup = "false")
    public void consumeEvents(OrderEvent order) {

        System.out.println("consumer consume the events: " + order.toString());

    }

}
