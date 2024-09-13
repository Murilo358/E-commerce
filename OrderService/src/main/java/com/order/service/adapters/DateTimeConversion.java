package com.order.service.adapters;

import java.time.*;

public class DateTimeConversion {

    public static long toEpochMillis(LocalDateTime dateTime) {
        ZonedDateTime zdt = dateTime.atZone(ZoneId.of(ZoneOffset.UTC.getId()));
        return zdt.toInstant().toEpochMilli();
    }

    public static LocalDateTime fromInstant(Instant instant) {
        if (instant != null) {

            return LocalDateTime.ofInstant(instant, ZoneId.of(ZoneOffset.UTC.getId()));

        }
        return null;
    }

}
