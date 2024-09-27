package com.fastturtle.swiftSeat.helpers;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class BusDataUtils {

    public static String formatBusNumber(String busNo) {
        // Format the bus number (e.g., "MH07HM7813" to "MH07 HM 7813")
        return busNo.replaceAll("(.{4})(.{2})(.{4})", "$1 $2 $3");
    }

    public static String formatBusTiming(LocalTime busTiming) {
        // Parse and format the bus timing (e.g., "07:00" to "7:00 PM")
        return busTiming.format(DateTimeFormatter.ofPattern("h:mm a"));
    }

}
