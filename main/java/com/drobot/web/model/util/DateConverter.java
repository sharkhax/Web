package com.drobot.web.util;

import java.time.LocalDate;

public class DateConverter {

    private static final double MILLIS_TO_DAYS = 1./(3600*24*1000);
    private static final int DAYS_TO_MILLIS = 24*3600*1000;

    private DateConverter() {
    }

    public static long daysToMillis(long days) {
        return days * DAYS_TO_MILLIS;
    }

    public static long millisToDays(long millis) {
        return (long) (millis * MILLIS_TO_DAYS);
    }

    public static LocalDate millisToLocalDate(long millis) {
        long days = millisToDays(millis);
        return LocalDate.ofEpochDay(days);
    }

    public static long localDateToMillis(LocalDate localDate) {
        long days = localDate.toEpochDay();
        return daysToMillis(days);
    }
}
