package com.fastturtle.redbusschemadesign.schema_initializers;

import com.fastturtle.redbusschemadesign.models.BankDetails;
import com.fastturtle.redbusschemadesign.models.CardDetails;
import com.fastturtle.redbusschemadesign.models.CardType;
import com.fastturtle.redbusschemadesign.repositories.BankDetailRepository;
import com.fastturtle.redbusschemadesign.repositories.CardDetailRepository;
import com.fastturtle.redbusschemadesign.repositories.PaymentRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class PaymentGatewayDataInitializer {

    private final CardDetailRepository cardDetailRepository;
    private final BankDetailRepository bankDetailRepository;

    @Autowired
    public PaymentGatewayDataInitializer(CardDetailRepository cardDetailRepository, BankDetailRepository bankDetailRepository) {

        this.cardDetailRepository = cardDetailRepository;
        this.bankDetailRepository = bankDetailRepository;
    }

    @PostConstruct
    @Transactional
    public void init() {
        insertPaymentData();
    }

    private void insertPaymentData() {

        // Card Details
        String[] cardNumbers = {"432111111234", "111123455432", "543211111234", "678943211234"};
        CardType[] cardTypes = {CardType.DEBIT, CardType.CREDIT, CardType.DEBIT, CardType.CREDIT};
        Byte[] expiryMonths = {11, 12, 8, 9};
        Integer[] expiryYears = {2027, 2029, 2026, 2025};
        String[] cvvs = {"123", "321", "456", "678"};

        // Bank Details
        String[] bankNames = {
                "HDFC Bank Ltd",
                "Axis Bank",
                "State Bank of India",
                "ICICI Bank",
                "Bank of Baroda",
                "Canara Bank",
                "Punjab National Bank",
                "Yes Bank Ltd",
                "Allahabad Bank",
                "Gramin Bank"
        };
        String[] bankCodes = {
                "HDFC1234567890123456",
                "AXIS1234567890123456",
                "SBI12345678901234567",
                "ICICI123456789012345",
                "BOB12345678901234567",
                "CANARA12345678901234",
                "PNB12345678901234567",
                "YES12345678901234567",
                "ALHBD123456789012345",
                "GRMN1234567890123456"
        };

        for(int i = 0; i < cardNumbers.length; i++) {
            CardDetails cardDetails = new CardDetails(cardNumbers[i], cardTypes[i], expiryMonths[i], expiryYears[i], cvvs[i], true);
            cardDetailRepository.save(cardDetails);

        }

        for(int i = 0; i < bankNames.length; i++) {
            BankDetails bankDetails = new BankDetails(bankNames[i], bankCodes[i]);
            bankDetailRepository.save(bankDetails);
        }


    }
}
