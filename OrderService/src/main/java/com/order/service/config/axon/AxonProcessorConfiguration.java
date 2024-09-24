package com.order.service.config.axon;


import org.axonframework.config.EventProcessingConfigurer;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AxonProcessorConfiguration {

    public AxonProcessorConfiguration(EventProcessingConfigurer configurer) {
        configurer.registerTrackingEventProcessor(
                "events",
                org.axonframework.config.Configuration::eventStore
        );
    }
}