package com.fastturtle.redbusschemadesign.controllers;

import com.fastturtle.redbusschemadesign.dtos.BookingRequest;
import com.fastturtle.redbusschemadesign.dtos.TravelRequest;
import com.fastturtle.redbusschemadesign.enums.BusType;
import com.fastturtle.redbusschemadesign.enums.Gender;
import com.fastturtle.redbusschemadesign.enums.SeatType;
import com.fastturtle.redbusschemadesign.helpers.BusDataUtils;
import com.fastturtle.redbusschemadesign.helpers.DateUtils;
import com.fastturtle.redbusschemadesign.helpers.SeatTypeEditor;
import com.fastturtle.redbusschemadesign.models.*;
import com.fastturtle.redbusschemadesign.security.CustomUserDetails;
import com.fastturtle.redbusschemadesign.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.*;

@Controller
@RequestMapping("/bookings")
public class BookingController {
    private final BookingService bookingService;
    private final UserService userService;
    private final RouteService routeService;
    private final BusService busService;

    @Autowired
    public BookingController(BookingService bookingService, UserService userService, RouteService routeService, BusService busService) {
        this.bookingService = bookingService;
        this.userService = userService;
        this.routeService = routeService;
        this.busService = busService;
    }

    @PostMapping("/book")
    public ResponseEntity<?> bookBus(@RequestBody BookingRequest bookingRequest) {
        return bookingService.bookBus(bookingRequest);
    }

    @GetMapping("/averageCostOnDate")
    public String showAverageCostForm(Principal principal, Model model) {
        model.addAttribute("loggedInUserName", principal.getName());
        return "averageCost";
    }

    @PostMapping("/averageCostOnDate")
    public String getAverageCostOfTicketsOnDate(@RequestParam String date, Model model, Principal principal) {
        ResponseEntity<?> response = bookingService.getAverageCostOfTicketsOnDate(
                LocalDate.parse(date));

        if (response.getStatusCode() == HttpStatus.OK) {
            Double averageCost = (Double) response.getBody();
            model.addAttribute("averageCost", averageCost);
        } else if(response.getStatusCode() == HttpStatus.BAD_REQUEST) {
            String errorMessage = ((Map<String, String>)response.getBody()).get("error");
            model.addAttribute("errorMessage", errorMessage);
        }

        model.addAttribute("loggedInUserName", principal.getName());

        return "averageCost";
    }

    @GetMapping("/fetchPassengersForBooking")
    public String fetchAllPassengersForBooking(@RequestParam(value = "bookingId", required = false) Integer bookingId, Model model, Principal principal) {
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

        model.addAttribute("loggedInUserName", principal.getName());

        return "findPassengersForBooking";
    }

    @GetMapping("/create")
    public String showPassengerForm(Model model, Principal principal) {
        List<Passenger> passengers = new ArrayList<>();
        passengers.add(new Passenger());

        Map<BusType, List<Bus>> busesByType = new HashMap<>();
        model.addAttribute("busesByType", busesByType);


        model.addAttribute("passengers", passengers);
        model.addAttribute("genders", Gender.values());
        model.addAttribute("seatPreferences", Arrays.asList("NO_PREFERENCE", SeatType.AISLE.name(), SeatType.WINDOW.name()));
        model.addAttribute("sources", routeService.findAllSources());
        model.addAttribute("destinations", routeService.findAllDestinations());
        model.addAttribute("loggedInUserName", principal.getName());

        return "bookingForm";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(SeatType.class, new SeatTypeEditor());
    }

    @PostMapping("/checkRouteAvailability")
    public String checkAvailability(@RequestParam("source") String source,
                                    @RequestParam("destination") String destination,
                                    @RequestParam("travelDate") String travelDate,
                                    Model model, Principal principal) {

        Map<BusType, List<Bus>> busesByType = busService.getAllBusesGroupedByTypeFilterBySourceAndDestination(source, destination);

        //  // Format bus number and bus timing for each bus
        for(Map.Entry<BusType, List<Bus>> entry : busesByType.entrySet()) {
            List<Bus> buses = entry.getValue();

            for(Bus b : buses) {
                double percentageAvailable = (double) (b.getAvailableSeats() * 100) / b.getTotalSeats();
                String formattedBusNo = BusDataUtils.formatBusNumber(b.getBusNo());
                String formattedBusTime = BusDataUtils.formatBusTiming(b.getBusTiming());

                b.setBusNo(formattedBusNo);
                b.setFormattedBusTiming(formattedBusTime);
                
                if(b.getAvailableSeats() <= 5 || percentageAvailable <= 10) {
                    b.setStyleClass("red-text");
                } else if(b.getAvailableSeats() > 5 && percentageAvailable > 10 && percentageAvailable <= 40) {
                    b.setStyleClass("yellow-text");
                } else if(percentageAvailable > 40) {
                    b.setStyleClass("green-text");
                } else {
                    b.setStyleClass("");
                }
            }

        }

        model.addAttribute("busesByType", busesByType);

        model.addAttribute("genders", Gender.values());
        model.addAttribute("seatPreferences", Arrays.asList("NO_PREFERENCE", SeatType.AISLE.name(), SeatType.WINDOW.name()));
        model.addAttribute("users", userService.findAll());
        model.addAttribute("sources", routeService.findAllSources());
        model.addAttribute("destinations", routeService.findAllDestinations());
        model.addAttribute("selectedSource", source);
        model.addAttribute("selectedDestination", destination);
        model.addAttribute("selectedTravelDate", travelDate);

        if (source.equals(destination)) {
            model.addAttribute("errorMessage", "Source and destination cannot be the same.");
            model.addAttribute("loggedInUserName", principal.getName());
            
            return "bookingForm"; // Return to the same form view with an error message
        } else {

            // Add any additional logic to check availability if needed
            List<Bus> availableBuses = busService.findAvailableBusesBySourceAndDestination(source, destination);

            if (availableBuses.isEmpty()) {
                model.addAttribute("errorMessage", "No available buses found for given source and destination.");
                model.addAttribute("loggedInUserName", principal.getName());

                return "bookingForm";
            } else {
                Set<BusType> availableBusTypes = new HashSet<>();
                for(Bus bus : availableBuses) {
                    availableBusTypes.add(bus.getBusType());
                }
                model.addAttribute("successMessage", "Hooray! Buses are available");
                model.addAttribute("busTypes", availableBusTypes);
            }
        }

        // Clear any existing error message
        model.addAttribute("errorMessage", null);
        model.addAttribute("loggedInUserName", principal.getName());

        return "bookingForm"; // Return to the same form view
    }

    @PostMapping("/create")
    public String createBooking(@RequestParam(value = "addUserAsPassenger", required = false) String addUserAsPassenger,
                                Principal principal,
                                @RequestParam("source") String source,
                                @RequestParam("destination") String destination,
                                @RequestParam(value = "busType", required = false) BusType busType,
                                @RequestParam(value = "seatTypeForUser", required = false) SeatType seatTypeForUser,
                                @RequestParam(value = "travelDate", required = false) String travelDate,
                                @RequestParam("busId") int selectedBusId,
                                @ModelAttribute("booking") Booking booking, Model model) {

        // Check if the checkbox was checked
        Integer userId = null;
        boolean isUserPassenger = false;
        if (principal != null) {
            CustomUserDetails userDetails = (CustomUserDetails) ((Authentication) principal).getPrincipal();
            userId = userDetails.getUserId();
        }
        if("on".equals(addUserAsPassenger)) {
            isUserPassenger = true;
        }

        if(busType == null) {
            model.addAttribute("errorMessage", "No bus type selected.");
            model.addAttribute("genders", Gender.values());
            model.addAttribute("seatPreferences", Arrays.asList("NO_PREFERENCE", SeatType.AISLE.name(), SeatType.WINDOW.name()));
            model.addAttribute("sources", routeService.findAllSources());
            model.addAttribute("destinations", routeService.findAllDestinations());

            List<Bus> availableBuses = busService.findAvailableBusesBySourceAndDestination(source, destination);

            Set<BusType> availableBusTypes = new HashSet<>();
            for(Bus bus : availableBuses) {
                availableBusTypes.add(bus.getBusType());
            }
            model.addAttribute("busTypes", availableBusTypes);

            model.addAttribute("selectedSource", source);
            model.addAttribute("selectedDestination", destination);
            model.addAttribute("selectedTravelDate", travelDate);

            if(principal != null) {
                model.addAttribute("loggedInUserName", principal.getName());
            }


            return "bookingForm";
        }

        // Here, booking.getPassengers() should return the list of passengers populated from the form
        ResponseEntity<?> response = bookingService.doBookingFromPassengerForm(userId, isUserPassenger, seatTypeForUser, source, destination, travelDate, selectedBusId, booking.getPassengers());
        if(response.getStatusCode() == HttpStatus.OK) {
            booking = (Booking) response.getBody();

            if(booking != null) {

                // Formatted bus no. and bus timing
                String formattedBusNumber = BusDataUtils.formatBusNumber(booking.getBusRoute().getBus().getBusNo());
                booking.getBusRoute().getBus().setFormattedBusNumber(formattedBusNumber);
                String formattedBusTiming = BusDataUtils.formatBusTiming(booking.getBusRoute().getBus().getBusTiming());
                booking.getBusRoute().getBus().setFormattedBusTiming(formattedBusTiming);

                model.addAttribute("booking", booking);

                // Formatted dates with ordinal suffixes
                String formattedBookingDate = DateUtils.formatWithOrdinalSuffix(booking.getBookingDate());
                String formattedTravelDate = DateUtils.formatWithOrdinalSuffix(booking.getTravelDate());

                // Added formatted dates to the model
                model.addAttribute("formattedBookingDate", formattedBookingDate);
                model.addAttribute("formattedTravelDate", formattedTravelDate);
            }

        } else if(response.getStatusCode() == HttpStatus.BAD_REQUEST) {
            String errorMessage = ((Map<String, String>) response.getBody()).get("error");

            if(principal != null) {
                model.addAttribute("loggedInUserName", principal.getName());
            }
            model.addAttribute("errorMessage", errorMessage);
            return "bookingForm";
        }

        return "bookingResult";

    }

    @GetMapping("/bookingResult")
    public String showBookingResult(@RequestParam("bookingId") int bookingId, Model model) {
        // Fetch the booking from the database using the bookingId
        Booking booking = bookingService.findByBookingId(bookingId).get();

        // Add the booking to the model
        model.addAttribute("booking", booking);

        return "bookingResult";
    }

    @GetMapping("/check-auth")
    public ResponseEntity<String> checkAuthStatus(Principal principal) {
        if (principal != null) {
            CustomUserDetails userDetails = (CustomUserDetails) ((Authentication) principal).getPrincipal();
            return ResponseEntity.ok("Authenticated as: " + userDetails.getUsername());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not authenticated");
        }
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateTravel(@RequestBody TravelRequest travelRequest) {
        return bookingService.updateTravelStatus(travelRequest);
    }

    @GetMapping("/findPassengersTraveledOnDate")
    public String showFindPassengersTravelledOnDatePage(Principal principal, Model model) {
        model.addAttribute("loggedInUserName", principal.getName());
        return "findPassengersTraveledOnDate";
    }

    @PostMapping("/findPassengersTraveledOnDate")
    public String findNumberOfPassengersTravelledOnDate(@RequestParam("travelDate") String travelDate, Model model, Principal principal) {
        Optional<List<Passenger>> passengers = bookingService.findPassengersTraveledOnDate(LocalDate.parse(
                travelDate));

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

        model.addAttribute("loggedInUserName", principal.getName());


        return "findPassengersTraveledOnDate";
    }

    //TODO: create a complete bus booking flow
    //TODO: 1. & person should be given option to select source and destination from dropdown
    //TODO: 2. & Put validation that source and destination should be different
    //TODO: 3. & Give option to choose date(or type date, whichever is feasible)
    //TODO: 4. & Give option to choose type of bus(if bus is available on chosen route)
    //TODO: 5. & Then give option to input passenger details
    //TODO: 6. & Also give option to add more passengers, then depending on it, again load that passenger detail form below.every passenger detail should have dropdown for preference of seat(aisle, window, no preference)
    //TODO: 7. & After its done, button should be there to save passengers(it saves passengers and booking as well)
    //TODO: 8. & Then, Give total payable amount option on next page, depending on seat type chosen
    //TODO: 9. & Then, this page will be payment page(a dummy one), where there should be three radio buttons(loaded from enum created in models), and options will be cash, credit card, wallet and debit card
    //TODO: 10.& Then, there should be three buttons below, mark as pending, mark as completed, or mark as failed, which saves payment accordingly.
    //TODO: 11.& Once it is done, payment status with amount is saved to database.
    //TODO: 12.& Then, from index page, there should be option to mark a passenger as traveled
    //TODO: 13.& Input given will be passenger id, once it is given, those passengers in that booking will be marked as traveled.

    //TODO: 14. & Payment process
    //TODO: 15. & Whenever booking completes, from bookingResult page itself, there should be option,
    //TODO: 16. & To do payment now, or do later
    //TODO: 17. & If paying now, doPayment template should open, with bookingId passed to it from
    //TODO: 18. & Previous template, and payment mode to be there as radio buttons
    //TODO: 19. & And, payable amount should be loaded from booking data itself.
    //TODO: 20. & Mark payment as successful, or mark as failed, two buttons to be there(a dummy payment to be done)
    //TODO: 21. & After clicking any of them, on next page, payment successfully updated message should come, with status of payment, completed or failed, and mode of payment chosen should also be shown
    //TODO: 22. & In Booking result template, after user name who is booked, route of booking, bus no. and booking date should be shown.
    //TODO: 23. & And with each passenger, his age, his gender, his seat number, his seat type should be shown

}
