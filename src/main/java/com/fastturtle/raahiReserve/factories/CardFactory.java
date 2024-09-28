package com.fastturtle.raahiReserve.factories;

import com.fastturtle.raahiReserve.dtos.CardDTO;
import com.fastturtle.raahiReserve.models.CardDetails;

import java.util.List;

public interface CardFactory {
    CardDetails createCard(CardDTO dto, List<?> additionalData);
}
