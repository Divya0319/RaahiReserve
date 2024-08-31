package com.fastturtle.redbusschemadesign.controllers;

import com.fastturtle.redbusschemadesign.models.Booking;
import com.fastturtle.redbusschemadesign.services.BookingService;
import com.fastturtle.redbusschemadesign.services.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/passengers")
public class PassengerController {

    private final BookingService bookingService;
    private final PassengerService passengerService;

    @Autowired
    public PassengerController(BookingService bookingService, PassengerService passengerService) {
        this.bookingService = bookingService;
        this.passengerService = passengerService;
    }

    @GetMapping("/markTraveled")
    public String markTraveled(Principal principal, Model model) {
        model.addAttribute("loggedInUserName", principal.getName());
        return "markTraveled";
    }

    @PostMapping("/markTraveled")
    public String loadPassengers(@RequestParam("bookingId") Integer bookingId, Model model) {
        Booking booking = bookingService.findByBookingId(bookingId).orElse(null);

        if (booking == null) {
            model.addAttribute("errorMessage", "Booking ID not found");
            return "markTraveled";
        }

        model.addAttribute("booking", booking);
        return "markTraveled";
    }

    @PostMapping("/markAsTraveled")
    public String markAsTraveled(@RequestParam("bookingId") Integer bookingId,
                                 @RequestParam("passengerIds") List<Integer> passengerIds,
                                 RedirectAttributes redirectAttributes) {

        Booking booking = bookingService.findByBookingId(bookingId).orElse(null);
        passengerService.markPassengersAsTraveled(booking, passengerIds);

        redirectAttributes.addFlashAttribute("successMessage", "Passengers Marked as Traveled successfully");
        return "redirect:/passengers/markTraveled";
    }

}
