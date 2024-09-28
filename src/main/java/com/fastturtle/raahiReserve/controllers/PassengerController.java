package com.fastturtle.raahiReserve.controllers;

import com.fastturtle.raahiReserve.models.Booking;
import com.fastturtle.raahiReserve.models.Passenger;
import com.fastturtle.raahiReserve.models.User;
import com.fastturtle.raahiReserve.services.BookingService;
import com.fastturtle.raahiReserve.services.PassengerService;
import com.fastturtle.raahiReserve.services.UserService;
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
    private final UserService userService;

    private User user;

    @Autowired
    public PassengerController(BookingService bookingService, PassengerService passengerService, UserService userService) {
        this.bookingService = bookingService;
        this.passengerService = passengerService;
        this.userService = userService;
    }

    @GetMapping("/markTraveled")
    public String markTraveled(Principal principal, Model model) {
        if(user == null) {
            user = userService.findByUsername(principal.getName());
        }
        model.addAttribute("loggedInUserName", user.getFullName());
        return "markTraveled";
    }

    @PostMapping("/markTraveled")
    public String loadPassengers(@RequestParam("bookingId") Integer bookingId, Model model, Principal principal) {
        Booking booking = bookingService.findByBookingId(bookingId).orElse(null);

        if (booking == null) {
            model.addAttribute("errorMessage", "Booking ID not found");
            return "markTraveled";
        } else {

            boolean allTraveled = booking.getPassengers().stream().allMatch(Passenger::isTraveled);
            if (allTraveled) {
                model.addAttribute("alreadyTraveledMessage", "All passengers in this booking have traveled.");
                if(user == null) {
                    user = userService.findByUsername(principal.getName());
                }
                return "markTraveled";
            } else {
                booking.getPassengers().removeIf(Passenger::isTraveled);
            }

        }

        model.addAttribute("booking", booking);
        if(user == null) {
            user = userService.findByUsername(principal.getName());
        }
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
