package com.fastturtle.raahiReserve.helpers;

import com.fastturtle.raahiReserve.enums.PaymentMethod;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class PaymentMethodConverter implements AttributeConverter<PaymentMethod, String> {

    @Override
    public String convertToDatabaseColumn(PaymentMethod paymentMethod) {
        if(paymentMethod == null){
            return null;
        }
        // Converting the enum to a string representation with underscores
        return paymentMethod.name();
    }

    @Override
    public PaymentMethod convertToEntityAttribute(String dbData) {
        if(dbData == null || dbData.isEmpty()){
            return null;
        }
        // Converting the string from the database back to the enum
        return PaymentMethod.valueOf(dbData);
    }
}
