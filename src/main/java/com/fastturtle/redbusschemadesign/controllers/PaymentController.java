package com.fastturtle.redbusschemadesign.controllers;

import com.fastturtle.redbusschemadesign.dtos.PaymentRequest;
import com.fastturtle.redbusschemadesign.models.Booking;
import com.fastturtle.redbusschemadesign.enums.PaymentMethod;
import com.fastturtle.redbusschemadesign.models.User;
import com.fastturtle.redbusschemadesign.models.UserWallet;
import com.fastturtle.redbusschemadesign.services.BankDetailsService;
import com.fastturtle.redbusschemadesign.services.BookingService;
import com.fastturtle.redbusschemadesign.services.PaymentService;
import com.fastturtle.redbusschemadesign.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;
    private final BookingService bookingService;
    private final BankDetailsService bankDetailsService;
    private final UserService userService;

    @Autowired
    public PaymentController(PaymentService paymentService, BookingService bookingService, BankDetailsService bankDetailsService, UserService userService) {
        this.paymentService = paymentService;
        this.bookingService = bookingService;
        this.bankDetailsService = bankDetailsService;
        this.userService = userService;
    }

    @PostMapping("/pay")
    public ResponseEntity<?> makePayment(@RequestBody PaymentRequest paymentRequest) {
        return paymentService.makePayment(paymentRequest);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updatePayment(@RequestBody PaymentRequest paymentRequest) {
        return paymentService.updatePayment(paymentRequest);
    }

    @GetMapping("/status/{bookingId}")
    public ResponseEntity<?> getPaymentStatus(@PathVariable int bookingId) {
        return paymentService.getPaymentStatus(bookingId);
    }

    @PostMapping("/fetchBookingDetails")
    public String fetchBookingDetails(@RequestParam("bookingId") Integer bookingId, Model model, Principal principal) {
        ResponseEntity<?> response = paymentService.checkPaymentStatusAndReturnBookingForBookingId(bookingId);
        if (response.getStatusCode() == HttpStatus.OK) {
            model.addAttribute("booking", response.getBody());
            model.addAttribute("bankDetails", bankDetailsService.getAllBankDetails());
            User loggedInUser = userService.findByUsername(principal.getName());
            model.addAttribute("walletDetails", userService.getUserWalletByEmail(loggedInUser.getEmail()));
            model.addAttribute("isBookingIdPresent", true);
        } else if(response.getStatusCode() == HttpStatus.NOT_FOUND) {
            String errorMessage = ((Map<String, String>)response.getBody()).get("error");
            model.addAttribute("errorMessage", errorMessage);
            model.addAttribute("isBookingIdPresent", false);
        } else if(response.getStatusCode() == HttpStatus.BAD_REQUEST) {
            model.addAttribute("successMessage", ((Map<String, String>)response.getBody()).get("warning"));
            model.addAttribute("paidAmt", ((Map<String, Float>)response.getBody()).get("paidAmt"));
            model.addAttribute("chosenPaymentMode", ((Map<String, PaymentMethod>)response.getBody()).get("chosenPaymentMode"));
            model.addAttribute("isBookingIdPresent", false);
        }

        model.addAttribute("loggedInUserName", principal.getName());
        model.addAttribute("paymentModes", PaymentMethod.values());
        return "doPayment";
    }

    @GetMapping("/doPayment")
    public String showPaymentPage(@RequestParam(value = "bookingId", required = false) Integer bookingId, Model model, Principal principal) {

        if(bookingId != null) {
            Optional<Booking> booking = bookingService.findByBookingId(bookingId);
            if (booking.isPresent()) {
                model.addAttribute("booking", booking.get());
                model.addAttribute("paymentModes", PaymentMethod.values());
            } else {
                model.addAttribute("error", "Invalid Booking ID");
                return "doPayment";
            }
        }

        model.addAttribute("loggedInUserName", principal.getName());

        model.addAttribute("isBookingIdPresent", bookingId != null);
        return "doPayment";
    }

    @PostMapping("/doPayment")
    public String processPayment(@RequestParam("bookingId") Integer bookingId,
                                 @RequestParam("paymentMode") PaymentMethod paymentMode,
                                 @RequestParam("action") String action,
                                 RedirectAttributes redirectAttributes) {

        paymentService.processPayment(bookingId, paymentMode, action);

        // Adding success message
        redirectAttributes.addFlashAttribute("message", "Payment marked as " + action.toUpperCase() + " successfully.");

        // Redirecting to a confirmation page or back to the booking result
        return "redirect:/bookings/bookingResult?bookingId=" + bookingId;
    }

    @PostMapping("/saveSelectedPaymentMode")
    public String saveSelectedPaymentMode(@RequestParam("paymentMode") String selectedPaymentMode, Model model) {
        System.out.println("Selected Payment Mode: " + selectedPaymentMode);

        model.addAttribute("selectedPaymentMode", selectedPaymentMode);

        return "resultForPaymentModeSelection";
    }

    @PostMapping("/otpValidation")
    public String showValidateOtpPage(@RequestParam("paymentModeChosen") String paymentMode,
                                      @RequestParam(value = "selectedBankForPayment", required = false) String selectedBankForPayment,
                                      Model model, Principal principal) {
        String loggedInUserName = principal.getName();
        User user = userService.findByUsername(loggedInUserName);
        String last4DigitsOfNo = user.getPhoneNumber().substring(user.getPhoneNumber().length() - 4);
        model.addAttribute("last4DigitsOfMobNo", last4DigitsOfNo);
        model.addAttribute("paymentModeChosen", paymentMode);
        if(selectedBankForPayment != null) {
            model.addAttribute("selectedBankForPayment", selectedBankForPayment);
        }
        return "securePaymentGateway";
    }

}
