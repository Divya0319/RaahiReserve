package com.fastturtle.redbusschemadesign.helpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    // Define the date formats
    private static final SimpleDateFormat SOURCE_DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
    private static final SimpleDateFormat TARGET_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    // Method to convert date format
    public String convertDateFormat(String sourceDate) {
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

}
