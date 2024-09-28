package com.fastturtle.raahiReserve.controllers;

import com.fastturtle.raahiReserve.services.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@Controller
@RequestMapping("/wallet")
public class WalletController {

    private final WalletService walletService;

    @Autowired
    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @GetMapping("/addBalance")
    public String showWalletLoadScreen(@RequestParam("userID") Integer userID, @RequestParam("bookingId") Integer bookingId, Model model) {
        model.addAttribute("userID", userID);
        model.addAttribute("bookingId", bookingId);
        return "walletLoadForm";
    }

    @PostMapping("/addBalance")
    public String addBalanceToWallet(@RequestParam("userID") Integer userID, @RequestParam("bookingId") Integer bookingId, @RequestParam("amount") BigDecimal amount, Model model) {

        BigDecimal updatedBalance = walletService.addBalanceToUserWallet(userID, amount);

        model.addAttribute("updatedBalance", updatedBalance);
        model.addAttribute("bookingId", bookingId);
        model.addAttribute("isComingFromAddBalancePage", true);

        return "forward:/payments/fetchBookingDetails";
    }
}
