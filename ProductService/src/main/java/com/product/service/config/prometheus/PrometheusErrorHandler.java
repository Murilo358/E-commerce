package com.product.service.config.prometheus;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.axonframework.eventhandling.EventMessage;
import org.axonframework.eventhandling.EventMessageHandler;
import org.axonframework.eventhandling.ListenerInvocationErrorHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;

@Component
public class PrometheusErrorHandler implements ListenerInvocationErrorHandler {

    private static final Logger logger = LoggerFactory.getLogger(PrometheusErrorHandler.class);
    private final Counter errorCounter;

    public PrometheusErrorHandler(MeterRegistry meterRegistry) {
        this.errorCounter = Counter.builder("event.errors")
                .description("Number of event processing errors")
                .tag("type", "listener")
                .register(meterRegistry);
    }

    @Override
    public void onError(@Nonnull Exception exception, @Nonnull EventMessage<?> event, @Nonnull EventMessageHandler eventHandler)  {

        errorCounter.increment();
        logger.error("Error handling event: {}", event.getPayloadType().getTypeName(), exception);

    }
}
