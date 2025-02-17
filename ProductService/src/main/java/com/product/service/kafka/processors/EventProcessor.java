package com.product.service.kafka.processors;

public interface EventProcessor<T> {
    void process(T event);
}

