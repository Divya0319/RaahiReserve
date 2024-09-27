package com.fastturtle.swiftSeat.helpers;

import com.fastturtle.swiftSeat.enums.SeatType;

import java.beans.PropertyEditorSupport;

public class SeatTypeEditor extends PropertyEditorSupport {

    @Override
    public void setAsText(String text) {
        if (text == null || text.trim().isEmpty() || "No Preference".equals(text)) {
            setValue(null); // Set to null for empty string or "No Preference"
        } else {
            setValue(SeatType.valueOf(text.toUpperCase())); // Convert text to the actual enum
        }
    }
}
