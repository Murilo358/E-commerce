package com.order.service.config.kafka.tests;

public interface EventProcessor<T> {
    void process(T event);
}

