package com.fastturtle.swiftSeat.services;

import com.fastturtle.swiftSeat.models.UserWallet;
import com.fastturtle.swiftSeat.repositories.UserWalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class WalletService {

    private final UserWalletRepository userWalletRepository;

    @Autowired
    public WalletService(UserWalletRepository userWalletRepository) {
        this.userWalletRepository = userWalletRepository;
    }

    public BigDecimal addBalanceToUserWallet(Integer userID, BigDecimal amount) {
        UserWallet userWallet = userWalletRepository.findByUserId(userID);

        BigDecimal newBalance = userWallet.getBalance().add(amount);
        userWallet.setBalance(newBalance);

        userWalletRepository.save(userWallet);

        return newBalance;
    }
}
