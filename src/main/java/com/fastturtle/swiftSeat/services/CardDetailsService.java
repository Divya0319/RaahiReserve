package com.fastturtle.swiftSeat.services;

import com.fastturtle.swiftSeat.models.CardDetails;
import com.fastturtle.swiftSeat.repositories.CardDetailRepository;
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
