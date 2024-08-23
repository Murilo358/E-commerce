package com.order.service.kafka;


import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Service;

@Service
@CommonsLog(topic = "Consumer Logger")
class KafkaListeners {


//  @KafkaListener(topics = "orders", groupId = "group_id")
//  public void consume(ConsumerRecord<String, OrderCreated> record) {
//    log.info(String.format("Consumed message -> %s", record.value()));
//  }
}