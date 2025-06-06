package com.order.service.kafka.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.order.service.kafka.processors.EventProcessor;
import com.order.service.kafka.routers.EventProcessorRegistry;
import com.order.service.kafka.routers.EventTypeRegistry;
import com.order.service.kafka.routers.KafkaConsumerTopicRouter;
import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.apache.avro.generic.GenericData;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
@Component
public class CustomKafkaConsumer {

    private static final Logger logger = LoggerFactory.getLogger(CustomKafkaConsumer.class);

    private final KafkaConsumerTopicRouter topicRouter;
    private final EventProcessorRegistry processorRegistry;

    private final EventTypeRegistry eventTypeRegistry;

    private KafkaConsumer<String, Object> consumer;
    private ExecutorService executorService;

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

//    @Value("${spring.kafka.consumer.group-id}")
//    private String groupId;
//
//    @Value("${spring.kafka.schema.registry-url}")
//    private String schemaRegistryUrl;

    public CustomKafkaConsumer(EventTypeRegistry eventTypeRegistry, KafkaConsumerTopicRouter topicRouter, EventProcessorRegistry processorRegistry) {
        this.eventTypeRegistry = eventTypeRegistry;
        this.topicRouter = topicRouter;
        this.processorRegistry = processorRegistry;
    }

    public KafkaConsumer<String, Object> createKafkaConsumer() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "group-id");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class);
        props.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://localhost:8090");
        return new KafkaConsumer<>(props);
    }

    @PostConstruct
    public void init() {
        this.consumer = createKafkaConsumer();
        subscribeToAllTopics();

        this.executorService = Executors.newSingleThreadExecutor();
        executorService.submit(this::consumeMessages);
    }

    private void subscribeToAllTopics() {
        Set<String> topics = new HashSet<>(topicRouter.getAllTopics());
        consumer.subscribe(topics);
        logger.info("Subscribed to topics: {}", topics);
    }

    private void consumeMessages() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                ConsumerRecords<String, Object> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, Object> record : records) {
                    handleRecord(record);
                }
            }
        } catch (Exception e) {
            logger.error("Error while consuming messages", e);
        } finally {
            consumer.close();
        }
    }

    private void handleRecord(ConsumerRecord<String, Object> record) {

        String topic = record.topic();
        try {
            String eventClassName = ((GenericData.Record) record.value()).getSchema().getName();
            Class<?> eventClass = eventTypeRegistry.getEventClass(eventClassName);
            if (eventClass != null) {
                ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

                Object event = objectMapper.readValue(record.value().toString(), eventClass);

                EventProcessor<Object> processor = processorRegistry.getProcessor(event.getClass());

                if (processor != null) {
                    processor.process(event);
                } else {
                    logger.warn("No processor found for event type: {}", eventClass);
                }
            }

        } catch (Exception e) {
            logger.error("Failed to process record from topic {}: {}", topic, record.value(), e);
        }
    }

    @PreDestroy
    public void shutdown() {
        if (executorService != null) {
            executorService.shutdown();
            try {
                if (!executorService.awaitTermination(10, TimeUnit.SECONDS)) {
                    executorService.shutdownNow();
                }
            } catch (InterruptedException e) {
                executorService.shutdownNow();
            }
        }
    }
}
