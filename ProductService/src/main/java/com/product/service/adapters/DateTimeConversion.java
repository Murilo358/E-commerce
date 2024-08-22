package com.product.service.adapters;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class DateTimeConversion {

    public static long toEpochMillis(LocalDateTime dateTime) {
        ZonedDateTime zdt = dateTime.atZone(ZoneId.systemDefault());
        return zdt.toInstant().toEpochMilli();
    }

    public static LocalDateTime fromEpochMillis(Long millis) {
        if (millis != null) {

            Instant instant = Instant.ofEpochMilli(millis);
            return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());

        }
        return null;
    }
}
