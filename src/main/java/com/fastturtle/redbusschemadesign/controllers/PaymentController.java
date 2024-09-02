package com.fastturtle.redbusschemadesign.controllers;

import com.fastturtle.redbusschemadesign.dtos.PaymentRequest;
import com.fastturtle.redbusschemadesign.models.Booking;
import com.fastturtle.redbusschemadesign.models.Payment;
import com.fastturtle.redbusschemadesign.models.PaymentMethods;
import com.fastturtle.redbusschemadesign.services.BookingService;
import com.fastturtle.redbusschemadesign.services.PaymentService;
//import io.swagger.v3.oas.annotations.Hidden;
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

    @Autowired
    public PaymentController(PaymentService paymentService, BookingService bookingService) {
        this.paymentService = paymentService;
        this.bookingService = bookingService;
    }

    @PostMapping("/pay")
//    @Hidden
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
            model.addAttribute("isBookingIdPresent", true);
        } else if(response.getStatusCode() == HttpStatus.NOT_FOUND) {
            String errorMessage = ((Map<String, String>)response.getBody()).get("error");
            model.addAttribute("errorMessage", errorMessage);
            model.addAttribute("isBookingIdPresent", false);
        } else if(response.getStatusCode() == HttpStatus.BAD_REQUEST) {
            model.addAttribute("successMessage", ((Map<String, String>)response.getBody()).get("warning"));
            model.addAttribute("paidAmt", ((Map<String, Float>)response.getBody()).get("paidAmt"));
            model.addAttribute("chosenPaymentMode", ((Map<String, PaymentMethods>)response.getBody()).get("chosenPaymentMode"));
            model.addAttribute("isBookingIdPresent", false);
        }

        model.addAttribute("loggedInUserName", principal.getName());
        model.addAttribute("paymentModes", PaymentMethods.values());
        return "doPayment";
    }

    @GetMapping("/doPayment")
    public String showPaymentPage(@RequestParam(value = "bookingId", required = false) Integer bookingId, Model model, Principal principal) {

        if(bookingId != null) {
            Optional<Booking> booking = bookingService.findByBookingId(bookingId);
            if (booking.isPresent()) {
                model.addAttribute("booking", booking.get());
                model.addAttribute("paymentModes", PaymentMethods.values());
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
                                 @RequestParam("paymentMode") PaymentMethods paymentMode,
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
}
