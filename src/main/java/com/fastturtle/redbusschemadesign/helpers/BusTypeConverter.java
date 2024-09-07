package com.fastturtle.redbusschemadesign.helpers;

import com.fastturtle.redbusschemadesign.enums.BusType;
import com.fastturtle.redbusschemadesign.enums.PaymentMethod;
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
