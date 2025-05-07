package com.order.service.kafka.publisher;

import com.order.service.kafka.routers.KafkaProducerTopicRouter;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Service
public class KafkaPublisher {

    private static final Logger log = LoggerFactory.getLogger(KafkaPublisher.class);
    private final KafkaTemplate<String, Object> kafkaTemplate;

    private final KafkaProducerTopicRouter kafkaProducerTopicRouter;

    public KafkaPublisher(KafkaTemplate<String, Object> kafkaTemplate, KafkaProducerTopicRouter kafkaProducerTopicRouter) {
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaProducerTopicRouter = kafkaProducerTopicRouter;
    }

    @Retryable(
            value = {KafkaException.class},
            maxAttempts = 3,
            backoff = @Backoff(delay = 2000)
    )

    @CircuitBreaker(name = "kafkaBreaker", fallbackMethod = "fallbackMethod")
    public void send(String key, Object value) {

        String topic = kafkaProducerTopicRouter.getTopicForEvent(value);

        sendTo(topic, key, value);
    }

    @Retryable(
            value = {KafkaException.class},
            maxAttempts = 3,
            backoff = @Backoff(delay = 2000)
    )
    @CircuitBreaker(name = "kafkaBreaker", fallbackMethod = "fallbackMethod")
    public void sendTo(String topic, String key, Object value) {


        kafkaTemplate.send(topic, key, value)
                .thenAccept(result -> {
                    log.debug("Message with key {} and value {} sent successfully to topic {} " ,key, value, topic);
                })
                .exceptionally(ex -> {
                    log.error("Error while sending message to topic {}", topic, ex);
                    sendToDlq(topic, key, value);
                    return null;
                });
    }


    private void sendToDlq(String topic, String key, Object value) {
        String dlqTopic = topic + "-dlq";

        kafkaTemplate.send(dlqTopic, key, value)
                .thenAccept(result -> log.info("Message {} sent to dead letter queue, topic: {} ", value, dlqTopic))
                .exceptionally(ex -> {
                    log.error("Error while sending message to topic {}", topic, ex);
                    return null;
                });
    }


    public void fallbackMethod(String topic, String key, Object value, Throwable t) {
        log.error("Circuit breaker activated: {}", t.getMessage());
        sendToDlq(topic, key, value);
    }
}
