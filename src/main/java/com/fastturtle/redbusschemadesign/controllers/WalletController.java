package com.fastturtle.redbusschemadesign.controllers;

import com.fastturtle.redbusschemadesign.services.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String showWalletLoadScreen(@RequestParam("userID") Integer userID, Model model) {
        model.addAttribute("userID", userID);
        return "walletLoadForm";
    }

    @PostMapping("/addBalance")
    public String addBalanceToWallet(@RequestParam("userID") Integer userID, @RequestParam("amount") BigDecimal amount, RedirectAttributes redirectAttributes) {

        BigDecimal updatedBalance = walletService.addBalanceToUserWallet(userID, amount);

        redirectAttributes.addFlashAttribute("updatedBalance", updatedBalance);

        return "redirect:/payments/fetchBookingDetails";
    }
}
