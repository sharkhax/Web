package com.drobot.web.util;

import java.time.LocalDate;

/**
 * Class used to convert date and time.
 *
 * @author Vladislav Drobot
 */
public class DateConverter {

    private static final double MILLIS_TO_DAYS = 1. / (3600 * 24 * 1000);
    private static final int DAYS_TO_MILLIS = 24 * 3600 * 1000;

    private DateConverter() {
    }

    /**
     * Converts days to milliseconds.
     *
     * @param days long value of days.
     * @return long value of milliseconds.
     */
    public static long daysToMillis(long days) {
        return days * DAYS_TO_MILLIS;
    }

    /**
     * Converts milliseconds to days.
     *
     * @param millis long value of milliseconds.
     * @return long value of days.
     */
    public static long millisToDays(long millis) {
        return (long) (millis * MILLIS_TO_DAYS);
    }

    /**
     * Converts milliseconds to LocalDate object.
     *
     * @param millis long value of milliseconds.
     * @return LocalDate object.
     */
    public static LocalDate millisToLocalDate(long millis) {
        long days = millisToDays(millis);
        return LocalDate.ofEpochDay(days);
    }

    /**
     * Converts LocalDate object to milliseconds.
     *
     * @param localDate LocalDate object.
     * @return long value of milliseconds.
     */
    public static long localDateToMillis(LocalDate localDate) {
        long days = localDate.toEpochDay();
        return daysToMillis(days);
    }
}
