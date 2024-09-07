package com.fastturtle.redbusschemadesign.helpers;

import com.fastturtle.redbusschemadesign.enums.SeatType;

import java.beans.PropertyEditorSupport;

public class SeatTypeEditor extends PropertyEditorSupport {

    @Override
    public void setAsText(String text) {
        if ("No Preference".equals(text)) {
            setValue(null); // Set to null to handle "No Preference"
        } else {
            setValue(SeatType.valueOf(text.toUpperCase())); // Convert text to the actual enum
        }
    }
}
