package com.fastturtle.raahiReserve.helpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtils {

    // Define the date formats
    private static final SimpleDateFormat SOURCE_DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
    private static final SimpleDateFormat TARGET_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    // New formatters for LocalDateTime
    private static final DateTimeFormatter SOURCE_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private static final DateTimeFormatter TARGET_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");


    // Method to convert date format
    public static String convertDateFormat(String sourceDate) {
        try {
            // Parse the date from the source format
            Date date = SOURCE_DATE_FORMAT.parse(sourceDate);
            // Format the date to the target format
            return TARGET_DATE_FORMAT.format(date);
        } catch (ParseException e) {
            // Handle parse exception
            System.err.println("Invalid date format: " + sourceDate);
            return null;
        }
    }

    // New method to handle String -> LocalDateTime -> formatted String
    public static String convertDateTimeFormat(String sourceDateTime) {
        try {
            // Parse from source format
            LocalDateTime dateTime = LocalDateTime.parse(sourceDateTime, SOURCE_DATE_TIME_FORMATTER);
            // Format to target format
            return dateTime.format(TARGET_DATE_TIME_FORMATTER);
        } catch (Exception e) {
            System.err.println("Invalid datetime format: " + sourceDateTime);
            return null;
        }
    }

    // Modified method to handle LocalDateTime with ordinal suffix
    public static String formatWithOrdinalSuffix(LocalDateTime sourceDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy 'at' HH:mm");
        String formattedDate = sourceDateTime.format(formatter);
        int day = sourceDateTime.getDayOfMonth();

        String suffix;
        if(day >= 11 && day <= 13) {
            suffix = "th";
        } else {
            switch (day % 10) {
                case 1: suffix = "st"; break;
                case 2: suffix = "nd"; break;
                case 3: suffix = "rd"; break;
                default: suffix = "th"; break;
            }
        }

        return formattedDate.replaceFirst("\\d+", day + suffix);
    }

    public static String formatTimeTo12HrFormat(LocalTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a");
        return time.format(formatter);
    }

}
