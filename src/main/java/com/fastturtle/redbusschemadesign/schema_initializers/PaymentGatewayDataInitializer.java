package com.fastturtle.redbusschemadesign.schema_initializers;

import com.fastturtle.redbusschemadesign.models.*;
import com.fastturtle.redbusschemadesign.repositories.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Component
public class PaymentGatewayDataInitializer {

    // Card Details
    String[] cardNumbers = {"432111111234", "111123455432", "543211111234", "678943211234"};
    CardType[] cardTypes = {
            CardType.DEBIT,
            CardType.CREDIT,
            CardType.DEBIT,
            CardType.CREDIT
    };
    Byte[] expiryMonths = {11, 12, 8, 9};
    Integer[] expiryYears = {2027, 2029, 2026, 2025};
    String[] cVVs = {"123", "321", "456", "678"};

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

    private final CardDetailRepository cardDetailRepository;
    private final BankDetailRepository bankDetailRepository;
    private final UserRepository userRepository;
    private final UserWalletRepository userWalletRepository;

    @Autowired
    public PaymentGatewayDataInitializer(CardDetailRepository cardDetailRepository, BankDetailRepository bankDetailRepository, UserRepository userRepository , UserWalletRepository userWalletRepository) {

        this.cardDetailRepository = cardDetailRepository;
        this.bankDetailRepository = bankDetailRepository;
        this.userRepository = userRepository;
        this.userWalletRepository = userWalletRepository;
    }

    @PostConstruct
    @Transactional
    public void init() {
        createAndSaveCardDetails();

        createAndSaveBankDetails();

        createAndSaveUserWallets();
    }

    private void createAndSaveCardDetails() {
        for(int i = 0; i < cardNumbers.length; i++) {
            CardDetails cardDetails = new CardDetails(cardNumbers[i], cardTypes[i], expiryMonths[i], expiryYears[i], cVVs[i], true);
            cardDetailRepository.save(cardDetails);

        }
    }

    private void createAndSaveBankDetails() {
        for(int i = 0; i < bankNames.length; i++) {
            BankDetails bankDetails = new BankDetails(bankNames[i], bankCodes[i]);
            bankDetailRepository.save(bankDetails);
        }
    }

    private void createAndSaveUserWallets() {
        List<User> users = userRepository.findAll();

        for(User user : users) {
            UserWallet uw = new UserWallet(BigDecimal.valueOf(2000));
            uw.setUser(user);
            userWalletRepository.save(uw);
        }
    }
}
