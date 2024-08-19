package com.order.service;


import com.order.service.avro.OrderCreated;
import lombok.extern.apachecommons.CommonsLog;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@CommonsLog(topic = "Consumer Logger")
class KafkaListeners {


  @KafkaListener(topics = "orders", groupId = "group_id")
  public void consume(ConsumerRecord<String, OrderCreated> record) {
    log.info(String.format("Consumed message -> %s", record.value()));
  }
}