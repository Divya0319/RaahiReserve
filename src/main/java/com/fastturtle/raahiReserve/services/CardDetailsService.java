package com.fastturtle.raahiReserve.services;

import com.fastturtle.raahiReserve.models.CardDetails;
import com.fastturtle.raahiReserve.repositories.CardDetailRepository;
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

    public CardDetails findByID(Integer id) {
        return cardDetailRepository.findById(id).orElse(null);
    }

    public CardDetails findByCardNumber(String cardNo) {
        return cardDetailRepository.findByCardNumber(cardNo);
    }
}
