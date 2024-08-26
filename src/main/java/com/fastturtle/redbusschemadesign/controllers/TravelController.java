package com.fastturtle.redbusschemadesign.controllers;

import com.fastturtle.redbusschemadesign.dtos.TravelRequest;
import com.fastturtle.redbusschemadesign.helpers.DateFormatConverter;
import com.fastturtle.redbusschemadesign.models.Booking;
import com.fastturtle.redbusschemadesign.models.Passenger;
import com.fastturtle.redbusschemadesign.services.BookingService;
import com.fastturtle.redbusschemadesign.services.PassengerService;
import com.fastturtle.redbusschemadesign.services.TravelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/travels")
public class TravelController {

    private final TravelService travelService;
    private final BookingService bookingService;
    private final PassengerService passengerService;

    @Autowired
    public TravelController(TravelService travelService, BookingService bookingService, PassengerService passengerService) {
        this.travelService = travelService;
        this.bookingService = bookingService;
        this.passengerService = passengerService;
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateTravel(@RequestBody TravelRequest travelRequest) {
        return travelService.updateTravelStatus(travelRequest);
    }

    @GetMapping("/passengersOnDate")
    public long getPassengersTraveledOnDate(@RequestParam LocalDate date) {
        return travelService.getPassengersTraveledOnDate(date);
    }

    @GetMapping("/markTraveled")
    public String markTraveled() {

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
    public String markAsTraveled(@RequestParam("bookingId") Long bookingId,
                                 @RequestParam("passengerIds") List<Integer> passengerIds,
                                 RedirectAttributes redirectAttributes) {

        List<Passenger> passengers = passengerService.findAllPassengersById(passengerIds);
        for (Passenger passenger : passengers) {
            passenger.setTraveled(true); // Assuming you have a `traveled` field in your `Passenger` entity
        }
        passengerService.saveAllPassengers(passengers);

        redirectAttributes.addFlashAttribute("successMessage", "Passengers Marked as Traveled successfully");
        return "redirect:/travels/markTraveled";
    }

    @GetMapping("/findPassengersTraveledOnDate")
    public String showFindPassengersTravelledOnDatePage() {
        return "findPassengersTraveledOnDate";
    }

    @PostMapping("/findPassengersTraveledOnDate")
    public String findNumberOfPassengersTravelledOnDate(@RequestParam("travelDate") String travelDate, Model model) {
        Optional<List<Passenger>> passengers = travelService.findPassengersTraveledOnDate(LocalDate.parse(
                new DateFormatConverter().convertDateFormat(travelDate)));

        if(passengers.isPresent()) {
            int count = passengers.get().size();
            model.addAttribute("passengers", passengers.get());
            model.addAttribute("count", passengers.get().size());
            if(count == 0) {
                model.addAttribute("errorMessage", "No Passengers found travelling on this date");
            }
        } else {
            model.addAttribute("errorMessage", "No Passengers found travelling on this date");

        }

        return "findPassengersTraveledOnDate";
    }
}
