package com.fastturtle.redbusschemadesign.controllers;

import com.fastturtle.redbusschemadesign.dtos.PaymentRequest;
import com.fastturtle.redbusschemadesign.models.Payment;
import com.fastturtle.redbusschemadesign.models.PaymentStatus;
import com.fastturtle.redbusschemadesign.services.PaymentService;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/pay")
    @Hidden
    public Payment makePayment(@RequestBody PaymentRequest paymentRequest) {
        return paymentService.makePayment(paymentRequest);
    }

    @PutMapping("/update")
    public Payment updatePayment(@RequestBody PaymentRequest paymentRequest) {
        return paymentService.updatePayment(paymentRequest);
    }

    @GetMapping("/status/{bookingId}")
    public PaymentStatus getPaymentStatus(@PathVariable int bookingId) {
        return paymentService.getPaymentStatus(bookingId);
    }
}
