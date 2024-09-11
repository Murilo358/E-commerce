package com.order.service.kafka.processors;

public interface EventProcessor<T> {
    void process(T event);
}

