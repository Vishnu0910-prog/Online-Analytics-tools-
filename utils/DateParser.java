package com.grd.online.paper.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class DateParser {

    public static LocalDateTime millisecondsToLocalDateTime(Long milliseconds) {
        Instant instant = Instant.ofEpochMilli(milliseconds);
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }
}
