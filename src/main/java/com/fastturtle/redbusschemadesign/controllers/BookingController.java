package com.fastturtle.redbusschemadesign.controllers;

import com.fastturtle.redbusschemadesign.dtos.BookingRequest;
import com.fastturtle.redbusschemadesign.helpers.DateFormatConverter;
import com.fastturtle.redbusschemadesign.models.Passenger;
import com.fastturtle.redbusschemadesign.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/bookings")
public class BookingController {
    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/book")
    public ResponseEntity<?> bookBus(@RequestBody BookingRequest bookingRequest) {
        return bookingService.bookBus(bookingRequest);
    }

    @GetMapping("/averageCostOnDate")
    public String showAverageCostForm() {
        return "averageCost";
    }

    @PostMapping("/averageCostOnDate")
    public String getAverageCostOfTicketsOnDate(@RequestParam String date, Model model) {
        ResponseEntity<?> response = bookingService.getAverageCostOfTicketsOnDate(LocalDate.parse
                (new DateFormatConverter().convertDateFormat(date)));

        if (response.getStatusCode() == HttpStatus.OK) {
            Double averageCost = (Double) response.getBody();
            model.addAttribute("averageCost", averageCost);
        } else if(response.getStatusCode() == HttpStatus.BAD_REQUEST) {
            String errorMessage = ((Map<String, String>)response.getBody()).get("error");
            model.addAttribute("errorMessage", errorMessage);
        }

        return "averageCost";
    }

    @GetMapping("/fetchPassengersForBooking")
    public String fetchAllPassengersForBooking(@RequestParam(value = "bookingId", required = false) Integer bookingId, Model model) {
        if(bookingId != null) {
            ResponseEntity<?> response = bookingService.fetchAllPassengersForBooking(bookingId);

            if(response.getStatusCode() == HttpStatus.OK) {
                List<Passenger> passengers = (List<Passenger>) response.getBody();
                model.addAttribute("passengers", passengers);
            } else if(response.getStatusCode() == HttpStatus.NOT_FOUND) {
                String errorMessage = ((Map<String, String>) response.getBody()).get("error");
                model.addAttribute("errorMessage", errorMessage);
            }
        }

        return "findPassengersForBooking";
    }

    //TODO: create a complete bus booking flow
    //TODO: 1. person should be given option to select source and destination from dropdown
    //TODO: 2. Put validation that source and destination should be different
    //TODO: 3. Give option to choose date(or type date, whichever is feasible)
    //TODO: 4. Give option to choose type of bus(if bus is available on chosen route)
    //TODO: 5. Then give option to input passenger details
    //TODO: 6. Also give option to add more passengers, then depending on it, again load that passenger detail form below.every passenger detail should have dropdown for preference of seat(aisle, window, no preference)
    //TODO: 7. After its done, button should be there to save passengers(it saves passengers and booking as well)
    //TODO: 8. Then, Give total payable amount option on next page, depending on seat type chosen
    //TODO: 9. Then, this page will be payment page(a dummy one), where there should be three radio buttons(loaded from enum created in models), and options will be cash, credit card, wallet and debit card
    //TODO: 10. Then, there should be three buttons below, mark as pending, mark as completed, or mark as failed, which saves payment accordingly.
    //TODO: 11. Once it is done, payment status with amount is saved to database.
    //TODO: 12. Then, from index page, there should be option to mark a passenger as traveled
    //TODO: 13. Input given will be passenger id, once it is given, those passengers in that booking will be marked as traveled.

}
