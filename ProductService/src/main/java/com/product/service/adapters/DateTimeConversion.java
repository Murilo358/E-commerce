package com.product.service.adapters;

import java.time.*;

public class DateTimeConversion {

    public static long toEpochMillis(LocalDateTime dateTime) {
        ZonedDateTime zdt = dateTime.atZone(ZoneId.of(ZoneOffset.UTC.getId()));
        return zdt.toInstant().toEpochMilli();
    }

    public static OffsetDateTime fromInstant(Instant instant) {
        if (instant != null) {

            //todo it should really be
            return OffsetDateTime.ofInstant(instant, ZoneId.of(ZoneOffset.UTC.getId()));

        }
        return null;
    }

}
