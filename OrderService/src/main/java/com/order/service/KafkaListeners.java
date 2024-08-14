package com.order.service;


import com.order.service.avro.OrderCreated;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaListeners {


    @KafkaListener(topics = "orders", groupId = "groupId", autoStartup = "true")
    public void consumeEvents(String order) {

        System.out.println("consumer consume the events: " + order);

    }

}
