package com.order.service.kafka.routers;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class KafkaTopicRouter {

    //TODO MAKE THIS USE EventProcessorType
    private final List<String> topicEventList = new ArrayList<>();

    public KafkaTopicRouter() {
        topicEventList.add("productCreated");
        topicEventList.add("productDeleted");
        topicEventList.add("productCreated");
        topicEventList.add("productDeleted");
        topicEventList.add("productInventoryUpdated");
        topicEventList.add("productUpdated");
        topicEventList.add("categoryCreated");
        topicEventList.add("orderCreated");
        topicEventList.add("categoryDeleted");
        topicEventList.add("categoryUpdated");
        topicEventList.add("promotionCreated");
        topicEventList.add("promotionDeleted");

    }


    public List<String> getAllTopics() {
        return topicEventList;
    }
}
