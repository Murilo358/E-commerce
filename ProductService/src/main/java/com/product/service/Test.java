package com.product.service;

import com.product.service.eventModels.OrderEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class Test {

    @Autowired
    KafkaTemplate<String, OrderEvent> kafkaTemplate;

    @GetMapping("/test")
    public void triggetTest(){
        kafkaTemplate.send("orders", new OrderEvent("1232131231231", "Produto teste", 123));
    }
}
