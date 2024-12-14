package com.product.service.config.axon;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.product.service.config.jackson.JacksonAvroModule;
import com.product.service.config.jackson.JacksonIgnoreAvroPropertiesMixIn;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.config.ConfigurerModule;
import org.axonframework.config.EventProcessingConfigurer;
import org.axonframework.eventhandling.EventMessage;
import org.axonframework.eventhandling.EventMessageHandler;
import org.axonframework.eventhandling.ListenerInvocationErrorHandler;
import org.axonframework.eventhandling.PropagatingErrorHandler;
import org.axonframework.serialization.Serializer;
import org.axonframework.serialization.json.JacksonSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.annotation.Nonnull;

@Slf4j
@Configuration
public class AxonConfiguration {

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
    public ConfigurerModule processingGroupErrorHandlingConfigurerModule() {
        return configurer -> configurer.eventProcessing(processingConfigurer ->
                processingConfigurer.registerDefaultListenerInvocationErrorHandler(
                                conf -> PropagatingErrorHandler.INSTANCE
                        )
                        .registerListenerInvocationErrorHandler(
                                "my-processing-group",
                                conf -> new ListenerInvocationErrorHandler() {
                                    @Override
                                    public void onError(@Nonnull Exception exception, @Nonnull EventMessage<?> event, @Nonnull EventMessageHandler eventHandler) throws Exception {
                                        log.error("Error while processing event: {}", event, exception);
                                    }

                                }
                        )
        );
    }
}


