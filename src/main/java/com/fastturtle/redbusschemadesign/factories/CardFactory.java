package com.fastturtle.redbusschemadesign.factories;

import com.fastturtle.redbusschemadesign.dtos.CardDTO;
import com.fastturtle.redbusschemadesign.models.CardDetails;

import java.util.List;

public interface CardFactory {
    CardDetails createCard(CardDTO dto, List<?> additionalData);
}
