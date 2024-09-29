package com.fastturtle.raahiReserve.controllers;

import com.fastturtle.raahiReserve.dtos.BookingRequest;
import com.fastturtle.raahiReserve.dtos.BookingSummaryDTO;
import com.fastturtle.raahiReserve.dtos.TravelRequest;
import com.fastturtle.raahiReserve.enums.BusType;
import com.fastturtle.raahiReserve.enums.Gender;
import com.fastturtle.raahiReserve.enums.SeatType;
import com.fastturtle.raahiReserve.helpers.BusDataUtils;
import com.fastturtle.raahiReserve.helpers.DateUtils;
import com.fastturtle.raahiReserve.helpers.SeatTypeEditor;
import com.fastturtle.raahiReserve.models.*;
import com.fastturtle.raahiReserve.security.CustomUserDetails;
import com.fastturtle.raahiReserve.services.*;
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
    private final SeatCostService seatCostService;

    private User user;

    @Autowired
    public BookingController(BookingService bookingService, UserService userService, RouteService routeService, BusService busService, SeatCostService seatCostService) {
        this.bookingService = bookingService;
        this.userService = userService;
        this.routeService = routeService;
        this.busService = busService;
        this.seatCostService = seatCostService;
    }

    @PostMapping("/book")
    public ResponseEntity<?> bookBus(@RequestBody BookingRequest bookingRequest) {
        return bookingService.bookBus(bookingRequest);
    }

    @GetMapping("/averageCostOnDate")
    public String showAverageCostForm(Principal principal, Model model) {

        if(user == null) {
            user = userService.findByUsername(principal.getName());
        }
        model.addAttribute("loggedInUserName", user.getFullName());
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

        if(user == null) {
            user = userService.findByUsername(principal.getName());
        }
        model.addAttribute("loggedInUserName", user.getFullName());

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

        if(user == null) {
            user = userService.findByUsername(principal.getName());
        }
        model.addAttribute("loggedInUserName", user.getFullName());

        return "findPassengersForBooking";
    }

    @GetMapping("/create")
    public String showPassengerForm(Model model, Principal principal) {
        List<Passenger> passengers = new ArrayList<>();
        passengers.add(new Passenger());

        Map<BusType, List<Bus>> busesByType = new HashMap<>();
        model.addAttribute("busesByType", busesByType);

        Map<BusType, List<SeatCost>> seatCostsByType = new HashMap<>();
        model.addAttribute("seatCostsByType", seatCostsByType);

        model.addAttribute("passengers", passengers);
        model.addAttribute("genders", Gender.values());
        model.addAttribute("seatPreferences", Arrays.asList("No Preference", SeatType.AISLE.name(), SeatType.WINDOW.name()));
        model.addAttribute("sources", routeService.findAllSources());
        model.addAttribute("destinations", routeService.findAllDestinations());
        if(user == null) {
            user = userService.findByUsername(principal.getName());
        }
        if(user == null) {
            user = userService.findByUsername(principal.getName());
        }
        model.addAttribute("loggedInUserName", user.getFullName());

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
        Map<BusType, List<SeatCost>> seatCostsByType = seatCostService.getAllSeatCostsGroupedByBusType();

        // Format bus number and bus timing for each bus
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
        model.addAttribute("seatCostsByType", seatCostsByType);

        model.addAttribute("genders", Gender.values());
        model.addAttribute("seatPreferences", Arrays.asList("No Preference", SeatType.AISLE.name(), SeatType.WINDOW.name()));
        model.addAttribute("users", userService.findAll());
        model.addAttribute("sources", routeService.findAllSources());
        model.addAttribute("destinations", routeService.findAllDestinations());
        model.addAttribute("selectedSource", source);
        model.addAttribute("selectedDestination", destination);
        model.addAttribute("selectedTravelDate", travelDate);

        if (source.equals(destination)) {
            model.addAttribute("errorMessage", "Source and destination cannot be the same.");
            if(user == null) {
                user = userService.findByUsername(principal.getName());
            }
            model.addAttribute("loggedInUserName", user.getFullName());
            
            return "bookingForm"; // Return to the same form view with an error message
        } else {

            // Add any additional logic to check availability if needed
            List<Bus> availableBuses = busService.findAvailableBusesBySourceAndDestination(source, destination);

            if (availableBuses.isEmpty()) {
                model.addAttribute("errorMessage", "No available buses found for given source and destination.");
                if(user == null) {
                    user = userService.findByUsername(principal.getName());
                }
                model.addAttribute("loggedInUserName", user.getFullName());

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
        if(user == null) {
            user = userService.findByUsername(principal.getName());
        }
        model.addAttribute("loggedInUserName", user.getFullName());

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
            model.addAttribute("seatPreferences", Arrays.asList("No Preference", SeatType.AISLE.name(), SeatType.WINDOW.name()));
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
                if(user == null) {
                    user = userService.findByUsername(principal.getName());
                }
                model.addAttribute("loggedInUserName", user.getFullName());
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

                BookingSummaryDTO bookingSummary = bookingService.createBookingSummary(booking);

                model.addAttribute("bookingSummary", bookingSummary);
            }

        } else if(response.getStatusCode() == HttpStatus.BAD_REQUEST) {
            String errorMessage = ((Map<String, String>) response.getBody()).get("error");

            if(principal != null) {
                if(user == null) {
                    user = userService.findByUsername(principal.getName());
                }
                model.addAttribute("loggedInUserName", user.getFullName());
            }
            model.addAttribute("errorMessage", errorMessage);
            return "bookingForm";
        }

        return "bookingResult";

    }

    @GetMapping("/bookingResult")
    public String showBookingResult(@RequestParam("bookingId") int bookingId, Model model) {
        Booking booking = bookingService.findByBookingId(bookingId).get();

        Bus b = booking.getBusRoute().getBus();
        String formattedBusNo = BusDataUtils.formatBusNumber(b.getBusNo());
        String formattedBusTime = BusDataUtils.formatBusTiming(b.getBusTiming());
        String formattedBookingDate = DateUtils.formatWithOrdinalSuffix(booking.getBookingDate());
        String formattedTravelDate = DateUtils.formatWithOrdinalSuffix(booking.getTravelDate());

        b.setFormattedBusNumber(formattedBusNo);
        b.setFormattedBusTiming(formattedBusTime);

        model.addAttribute("booking", booking);
        model.addAttribute("formattedBookingDate", formattedBookingDate);
        model.addAttribute("formattedTravelDate", formattedTravelDate);

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
        if(user == null) {
            user = userService.findByUsername(principal.getName());
        }
        model.addAttribute("loggedInUserName", user.getFullName());
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

        if(user == null) {
            user = userService.findByUsername(principal.getName());
        }
        model.addAttribute("loggedInUserName", user.getFullName());


        return "findPassengersTraveledOnDate";
    }

    @GetMapping("/bookingSummaryForm")
    public String showBookingSummaryForm(Principal principal, Model model) {
        if(principal != null) {
            if(user == null) {
                user = userService.findByUsername(principal.getName());
            }
            model.addAttribute("loggedInUserName", user.getFullName());
        }
        return "bookingSummary";
    }

    @GetMapping("/bookingSummary")
    public String showBookingSummary(@RequestParam("bookingId") int bookingId, Model model, Principal principal) {
        Booking booking = bookingService.findByBookingId(bookingId).orElse(null);

        if(principal != null) {
            if(user == null) {
                user = userService.findByUsername(principal.getName());
            }
            model.addAttribute("loggedInUserName", user.getFullName());
        }

        if(booking == null) {
            model.addAttribute("errorMessage", "No booking found for id " + bookingId);
        } else {
            Bus b = booking.getBusRoute().getBus();
            String formattedBusNo = BusDataUtils.formatBusNumber(b.getBusNo());
            String formattedBusTime = BusDataUtils.formatBusTiming(b.getBusTiming());
            String formattedBookingDate = DateUtils.formatWithOrdinalSuffix(booking.getBookingDate());
            String formattedTravelDate = DateUtils.formatWithOrdinalSuffix(booking.getTravelDate());

            b.setFormattedBusNumber(formattedBusNo);
            b.setFormattedBusTiming(formattedBusTime);

            model.addAttribute("booking", booking);
            model.addAttribute("formattedBookingDate", formattedBookingDate);
            model.addAttribute("formattedTravelDate", formattedTravelDate);

            BookingSummaryDTO bookingSummary = bookingService.createBookingSummary(booking);
            model.addAttribute("bookingSummary", bookingSummary);
        }

        return "bookingSummary";
    }

}
