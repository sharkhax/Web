package com.drobot.web.model.util;

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
}
