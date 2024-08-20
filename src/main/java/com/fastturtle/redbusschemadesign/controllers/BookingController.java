package com.fastturtle.redbusschemadesign.controllers;

import com.fastturtle.redbusschemadesign.dtos.BookingRequest;
import com.fastturtle.redbusschemadesign.helpers.DateFormatConverter;
import com.fastturtle.redbusschemadesign.models.*;
import com.fastturtle.redbusschemadesign.services.BookingService;
import com.fastturtle.redbusschemadesign.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/bookings")
public class BookingController {
    private final BookingService bookingService;
    private UserService userService;

    @Autowired
    public BookingController(BookingService bookingService, UserService userService) {
        this.bookingService = bookingService;
        this.userService = userService;
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

    @GetMapping("/selectBusType")
    public String showBusTypeSelectionForm(Model model) {
        model.addAttribute("busTypes", BusType.values());
        return "selectBusType";
    }

    @PostMapping("/saveSelectedBusType")
    public String saveSelectedBusType(@RequestParam("busType") String selectedBusType, Model model) {
        System.out.println("Selected Bus Type: " + selectedBusType);

        // Add the selected bus type back to the model if you want to display it on a new page.
        model.addAttribute("selectedBusType", selectedBusType);

        // Redirect or return a view name to avoid template resolution error.
        return "result"; // Change "result" to the name of your success page template.
    }

    @GetMapping("/selectPaymentMode")
    public String showPaymentModeSelectionForm(Model model) {
        model.addAttribute("paymentModes", PaymentMethods.values());
        return "doPayment";
    }

    @PostMapping("/saveSelectedPaymentMode")
    public String saveSelectedPaymentMode(@RequestParam("paymentMode") String selectedPaymentMode, Model model) {
        System.out.println("Selected Payment Mode: " + selectedPaymentMode);

        model.addAttribute("selectedPaymentMode", selectedPaymentMode);

        return "resultForPaymentModeSelection";
    }

    @GetMapping("/passengerForm")
    public String showPassengerForm(Model model) {
        List<Passenger> passengers = new ArrayList<>();
        passengers.add(new Passenger());

        model.addAttribute("passengers", passengers);
        model.addAttribute("genders", Gender.values());
        model.addAttribute("seatPreferences", Arrays.asList("NO_PREFERENCE", SeatType.AISLE.name(), SeatType.WINDOW.name()));
        model.addAttribute("users", userService.findAll());

        return "passenger_form";
    }

    @PostMapping("/submitPassengerDetails")
    public String submitPassengerDetails(@ModelAttribute("passengers") List<Passenger> passengers, Model model) {
        for (Passenger passenger : passengers) {
            System.out.println("Passenger: " + passenger.getName() + ", Age: " + passenger.getAge() +
                    ", Gender: " + passenger.getGender() + ", Seat Preference: " + passenger.getBusSeat().getSeatType());
        }

        model.addAttribute("passengers", passengers);
        model.addAttribute("genders", Gender.values());
        model.addAttribute("seatPreferences", Arrays.asList("NO_PREFERENCE", SeatType.AISLE.name(), SeatType.WINDOW.name()));

        return "passenger_form";
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
