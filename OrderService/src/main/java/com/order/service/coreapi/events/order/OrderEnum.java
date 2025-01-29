package com.order.service.coreapi.events.order;

import java.util.Arrays;

public enum OrderEnum {
    PENDING("pending"),
    APPROVED("approved"),
    CANCELED("canceled"),
    SHIPPED("shipped"),
    DELIVERED("delivered");

    private final String description;

    OrderEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static OrderEnum getByDescription(String description) {
        return Arrays.stream(OrderEnum.values())
                .filter(status -> status.getDescription().equalsIgnoreCase(description))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid description: " + description));
    }
}
