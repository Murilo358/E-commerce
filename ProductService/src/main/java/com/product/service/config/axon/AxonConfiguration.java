package com.product.service.config.axon;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.product.service.config.jackson.JacksonAvroModule;
import com.product.service.config.jackson.JacksonIgnoreAvroPropertiesMixIn;
import com.product.service.config.prometheus.PrometheusErrorHandler;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.common.jpa.EntityManagerProvider;
import org.axonframework.common.transaction.TransactionManager;
import org.axonframework.config.ConfigurerModule;
import org.axonframework.eventhandling.ListenerInvocationErrorHandler;
import org.axonframework.eventhandling.deadletter.jpa.JpaSequencedDeadLetterQueue;
import org.axonframework.serialization.Serializer;
import org.axonframework.serialization.json.JacksonSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Slf4j
@Configuration
public class AxonConfiguration {

    @Autowired
    private PrometheusErrorHandler errorHandler;

    @Autowired
    private MeterRegistry meterRegistry;

    @Bean
    @Primary
    public Serializer defaultSerializer() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.registerModule(new JacksonAvroModule());
        objectMapper.addMixIn(Object.class, JacksonIgnoreAvroPropertiesMixIn.class);

        return JacksonSerializer.builder().objectMapper(objectMapper).build();
    }

    @Bean
    public ConfigurerModule processorErrorHandlingConfigurerModule() {
        return configurer -> configurer.eventProcessing(processingConfigurer -> {
            processingConfigurer
                    .registerDefaultErrorHandler(conf -> errorContext -> {
                        Counter.builder("global.errors.count")
                                .description(errorContext.error().getMessage())
                                .tag("type", "global")
                                .register(meterRegistry)
                                .increment();


                    })
                    .registerErrorHandler("events", conf -> errorContext -> {
                        Counter.builder("my.processor.errors.count")
                                .description(errorContext.error().getMessage())
                                .tag("type", "processor")
                                .tag("name", "events")
                                .register(meterRegistry)
                                .increment();

                    });
        });
    }

    @Bean
    public ConfigurerModule processingGroupErrorHandlingConfigurerModule() {
        return configurer -> configurer.eventProcessing(processingConfigurer ->
                processingConfigurer
                        .registerDefaultListenerInvocationErrorHandler(conf -> errorHandler)
                        .registerListenerInvocationErrorHandler(
                                "events",
                                conf -> (exception, event, eventHandler) -> errorHandler.onError(exception, event, eventHandler)
                        )
        );
    }
    @Bean
    public ConfigurerModule deadLetterQueueConfigurerModule() {
        return configurer -> configurer.eventProcessing().registerDeadLetterQueue(
                "events",
                config -> JpaSequencedDeadLetterQueue.builder()
                        .processingGroup("events")
                        .maxSequences(3)
                        .maxSequenceSize(3)
                        .entityManagerProvider(config.getComponent(EntityManagerProvider.class))
                        .transactionManager(config.getComponent(TransactionManager.class))
                        .serializer(config.serializer())
                        .build()
        );
    }



    /**
     * ListenerInvocationErrorHandler personalizado.
     */
    @Bean
    public ListenerInvocationErrorHandler customListenerErrorHandler() {
        return (exception, event, eventHandler) -> {
            log.error("Erro em listener personalizado: {}", event.getPayloadType().getSimpleName(), exception);
            throw exception;
        };
    }
}


