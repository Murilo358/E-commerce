package com.product.service.order;



import com.sharedModels.shared.OrderEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("order")
public class OrderController {

    @Autowired
    KafkaTemplate<String, Object> kafkaTemplate;

    @GetMapping("/test")
    public void testOrder(){
        kafkaTemplate.send("orders", new OrderEvent("123131", "12313", 123));
    }

}
