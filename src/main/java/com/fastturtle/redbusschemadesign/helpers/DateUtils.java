package com.fastturtle.redbusschemadesign.helpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtils {

    // Define the date formats
    private static final SimpleDateFormat SOURCE_DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
    private static final SimpleDateFormat TARGET_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

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

    public static String formatWithOrdinalSuffix(LocalDate sourceDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy");
        String formattedDate = sourceDate.format(formatter);
        int day = sourceDate.getDayOfMonth();

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
