package com.fastturtle.redbusschemadesign.helpers;

import com.fastturtle.redbusschemadesign.models.SeatType;

import java.beans.PropertyEditorSupport;

public class SeatTypeEditor extends PropertyEditorSupport {

    @Override
    public void setAsText(String text) {
        if ("NO_PREFERENCE".equalsIgnoreCase(text)) {
            setValue(null); // Set to null to handle "NO_PREFERENCE"
        } else {
            setValue(SeatType.valueOf(text.toUpperCase())); // Convert text to the actual enum
        }
    }
}
