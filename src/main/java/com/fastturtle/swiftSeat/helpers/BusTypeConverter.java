package com.fastturtle.swiftSeat.helpers;

import com.fastturtle.swiftSeat.enums.BusType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class BusTypeConverter implements AttributeConverter<BusType, String> {

    @Override
    public String convertToDatabaseColumn(BusType busType) {
        if(busType == null){
            return null;
        }
        // Converting the enum to a string representation with underscores
        return busType.name();
    }

    @Override
    public BusType convertToEntityAttribute(String dbData) {
        if(dbData == null || dbData.isEmpty()){
            return null;
        }
        // Converting the string from the database back to the enum
        return BusType.valueOf(dbData);
    }
}
