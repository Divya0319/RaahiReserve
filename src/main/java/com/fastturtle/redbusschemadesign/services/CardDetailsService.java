package com.fastturtle.redbusschemadesign.services;

import com.fastturtle.redbusschemadesign.models.CardDetails;
import com.fastturtle.redbusschemadesign.repositories.CardDetailRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardDetailsService {

    private final CardDetailRepository cardDetailRepository;

    public CardDetailsService(CardDetailRepository cardDetailRepository) {
        this.cardDetailRepository = cardDetailRepository;
    }

    public List<CardDetails> findCardsForUser(Integer userID) {
        return cardDetailRepository.findCardsForUser(userID);
    }
}
