package com.order.service.config.kafka.tests;

import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;

import java.time.Duration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CustomKafkaConsumer {

    private final KafkaTopicRouter topicRouter;
    private final EventProcessorRegistry processorRegistry;
    private final KafkaConsumer<String, Object> consumer;

//    @Value("${spring.kafka.bootstrap-servers}")
//    private String bootstrapServers;

//    @Value("${spring.schema.registry}")
//    private String schemaRegistry;


    public CustomKafkaConsumer(KafkaTopicRouter topicRouter, EventProcessorRegistry processorRegistry) {
        this.topicRouter = topicRouter;
        this.consumer = createKafkaConsumer();
        this.processorRegistry = processorRegistry;
    }

    @Bean
    public KafkaConsumer<String, Object> createKafkaConsumer() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "group-id");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class);
        props.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://localhost:8090");
        return new KafkaConsumer<>(props);
    }

    public void subscribeToAllTopics() {
        Set<String> topics = new HashSet<>(topicRouter.getAllTopics());
        consumer.subscribe(topics);
    }

    public void consumeMessages() {
        while (true) {
            ConsumerRecords<String, Object> records = consumer.poll(Duration.ofMillis(100));

            for (ConsumerRecord<String, Object> record : records) {
                handleRecord(record);
            }
        }
    }


    private void handleRecord(ConsumerRecord<String, Object> record) {
        String topic = record.topic();

        try {
            Class<?> eventType = topicRouter.getEventTypeForTopic(topic);

            KafkaAvroDeserializer kafkaAvroDeserializer = new KafkaAvroDeserializer();

            EventProcessor<Object> processor = processorRegistry.getProcessor(eventType);

            if (processor != null) {
                processor.process(record.value());
            } else {
                System.err.println("No processor found for event type: " + eventType);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

