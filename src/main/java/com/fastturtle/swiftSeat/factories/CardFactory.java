package com.fastturtle.swiftSeat.factories;

import com.fastturtle.swiftSeat.dtos.CardDTO;
import com.fastturtle.swiftSeat.models.CardDetails;

import java.util.List;

public interface CardFactory {
    CardDetails createCard(CardDTO dto, List<?> additionalData);
}
