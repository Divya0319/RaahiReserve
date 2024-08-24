package com.fastturtle.redbusschemadesign.controllers;

import com.fastturtle.redbusschemadesign.dtos.PaymentRequest;
import com.fastturtle.redbusschemadesign.models.Booking;
import com.fastturtle.redbusschemadesign.models.Payment;
import com.fastturtle.redbusschemadesign.models.PaymentMethods;
import com.fastturtle.redbusschemadesign.services.BookingService;
import com.fastturtle.redbusschemadesign.services.PaymentService;
//import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @GetMapping("/doPayment")
    public String showPaymentPage(@RequestParam("bookingId") int bookingId, Model model) {
        Optional<Booking> booking = bookingService.findByBookingId(bookingId);
        model.addAttribute("booking", booking.get());
        model.addAttribute("paymentModes", PaymentMethods.values());
        return "doPayment";
    }

    @PostMapping("/doPayment")
    public String processPayment(@RequestParam("bookingId") int bookingId,
                                 @RequestParam("paymentMode") PaymentMethods paymentMode,
                                 @RequestParam("action") String action,
                                 RedirectAttributes redirectAttributes) {

        paymentService.processPayment(bookingId, paymentMode, action);

        // Add success message
        redirectAttributes.addFlashAttribute("message", "Payment marked as " + action + " successfully.");

        // Redirect to a confirmation page or back to the booking result
        return "redirect:/bookings/bookingResult?bookingId=" + bookingId;
    }

    @PostMapping("/saveSelectedPaymentMode")
    public String saveSelectedPaymentMode(@RequestParam("paymentMode") String selectedPaymentMode, Model model) {
        System.out.println("Selected Payment Mode: " + selectedPaymentMode);

        model.addAttribute("selectedPaymentMode", selectedPaymentMode);

        return "resultForPaymentModeSelection";
    }
}
