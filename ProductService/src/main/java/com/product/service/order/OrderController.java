package com.product.service.order;


import com.product.service.avro.Order;
import com.product.service.events.OrderEvent;
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
        kafkaTemplate.send("orders", new Order("1", "2", "3",
                "4", "5", 10, 50.0F, 10.0F, true));
    }


    //@Component
    //class PeoplePublisherImpl(
    //    private val kafkaTemplate: KafkaTemplate<String, Any>,
    //    private val avro: Avro
    //) : PeoplePublisher {
    //    private val logger = LoggerFactory.getLogger(javaClass)
    //
    //    override fun send(people: People) {
    //        this.kafkaTemplate.send("pessoa", this.avro.toRecord(People.serializer(), people))
    //            .handle { event, error ->
    //                if (error != null) logger.error("error at send message to topic Kafka")
    //                else logger.info("message sent with success, partition {}", event.recordMetadata.partition())
    //            }
    //    }
    //}

}
