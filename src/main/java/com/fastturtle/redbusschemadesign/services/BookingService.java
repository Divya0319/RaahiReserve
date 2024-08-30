package com.fastturtle.redbusschemadesign.services;

import com.fastturtle.redbusschemadesign.dtos.BookingRequest;
import com.fastturtle.redbusschemadesign.dtos.TravelRequest;
import com.fastturtle.redbusschemadesign.helpers.RandomSeatNumberProvider;
import com.fastturtle.redbusschemadesign.helpers.RandomSeatNumberProviderWithPreference;
import com.fastturtle.redbusschemadesign.models.*;
import com.fastturtle.redbusschemadesign.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;

    private final BusRouteRepository busRouteRepository;

    private final UserRepository userRepository;

    private final BusRepository busRepository;
    private final BusSeatRepository busSeatRepository;
    private final SeatCostRepository seatCostRepository;
    private final PassengerRepository passengerRepository;

    @Autowired
    public BookingService(BookingRepository bookingRepository, BusRouteRepository busRouteRepository, UserRepository userRepository, BusRepository busRepository, BusSeatRepository busSeatRepository, SeatCostRepository seatCostRepository, PassengerRepository passengerRepository) {
        this.bookingRepository = bookingRepository;
        this.busRouteRepository = busRouteRepository;
        this.userRepository = userRepository;
        this.busRepository = busRepository;
        this.busSeatRepository = busSeatRepository;
        this.seatCostRepository = seatCostRepository;
        this.passengerRepository = passengerRepository;
    }

    public ResponseEntity<?> bookBus(BookingRequest bookingRequest) {
        BusRoute busRoute = busRouteRepository.findFirstBusRouteBySourceAndDestination(bookingRequest.getSource(), bookingRequest.getDestination());

        if(busRoute == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Bus route not found"));
        }


        Optional<User> optionalUser = userRepository.findById(bookingRequest.getUserId());
        User user;
        if(optionalUser.isPresent()) {
            user = optionalUser.get();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        Bus bus = busRoute.getBus();
        ResponseEntity<?> response;
        if (bus.getAvailableSeats() < bookingRequest.getPassengerCount()) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "Not enough available seats");
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        } else {
            bus.setAvailableSeats(bus.getAvailableSeats() - bookingRequest.getPassengerCount());
            busRepository.save(bus);

            Booking booking = new Booking();
            booking.setBusRoute(busRoute);
            booking.setUser(user);
            booking.setBookingDate(LocalDate.now());
            response = ResponseEntity.ok(booking);
        }

        return response;
    }

    public Optional<Booking> findByBookingId(int bookingId) {
        return bookingRepository.findByBookingId(bookingId);
    }

    public ResponseEntity<?> getAverageCostOfTicketsOnDate(LocalDate date) {
        Double averageCost = bookingRepository.findAverageCostOfTicketsOnDate(date);
        ResponseEntity<?> response;
        if(averageCost == null) {
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "No bookings for given date"));
        } else {
            String formattedAverageString = String.format("%.2f", averageCost);
            Double averageCost2DecPlaces = Double.parseDouble(formattedAverageString);
            response = ResponseEntity.ok(averageCost2DecPlaces);
        }
        return response;
    }

    public ResponseEntity<?> fetchAllPassengersForBooking(int bookingId) {
        Optional<Booking> optionalBooking = bookingRepository.findByBookingId(bookingId);

        ResponseEntity<?> response;
        if(optionalBooking.isEmpty()) {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Booking not found"));
        } else {
           Booking booking = optionalBooking.get();
           List<Passenger> passengers = booking.getPassengers();


           response = ResponseEntity.ok(passengers);
        }

        return response;
    }

    public ResponseEntity<?> doBookingFromPassengerForm(Integer userId, boolean isUserPassenger,
                                                        SeatType seatTypeForUser, String source,
                                                        String destination, String travelDate, int selectedBusId, List<Passenger> passengers) {
        if(source.equals(destination)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Source and destination cannot be same"));
        }

        Booking booking = new Booking();
        Float bookingCost = 0.0f;


        Bus busForBooking = busRepository.findByBusId(selectedBusId);
        BusRoute busRouteForBooking = busRouteRepository.
                findFirstBusRouteBySourceAndDestination(source, destination);

        RandomSeatNumberProviderWithPreference rsnpwp = new RandomSeatNumberProviderWithPreference(busRepository, busSeatRepository);
        rsnpwp.setBusNo(busForBooking.getBusNo());

        if (userId != null && isUserPassenger) {
            User user = userRepository.findById(userId).orElse(null);
            booking.setUser(user);
            booking.setUserPassenger(true);

            BusSeat seatForUser;
            if(seatTypeForUser != null) {
                int assignedSeatForUer = rsnpwp.getRandomSeatNumberWithPreference(seatTypeForUser, true);
                seatForUser = new BusSeat();
                seatForUser.setBus(busForBooking);
                seatForUser.setSeatNumber(assignedSeatForUer);
                seatForUser.setSeatType(rsnpwp.getSeatTypeFromSeatNumber(assignedSeatForUer));
                seatForUser.setOccupied(true);
                seatForUser.setCreatedAt(ZonedDateTime.now(ZoneId.of("Asia/Kolkata")));
            } else {
                // Handle "NO_PREFERENCE" case, assign any available seat
                seatForUser = getBusSeatWithoutPreference(busForBooking);
            }
            busSeatRepository.save(seatForUser);

            BusType busTypeForUser = busSeatRepository.findBusTypeFromBusSeat(seatForUser);
            Float seatCostForUser = seatCostRepository.findCostByBusType(busTypeForUser);

            bookingCost += seatCostForUser;

            Passenger userPassenger = new Passenger();
            userPassenger.setName(booking.getUser().getUserName());
            userPassenger.setAge(booking.getUser().getAge());
            userPassenger.setGender(booking.getUser().getGender());
            userPassenger.setBusSeat(seatForUser);
            booking.addPassenger(userPassenger);

        } else if(userId != null){
            booking.setUser(userRepository.findById(userId).orElse(null));
            booking.setUserPassenger(false);
        }


        for(Passenger p : passengers) {
            p.setPassengerId(null);   // Ensure the ID is null, so JPA will treat it as a new entity.
            BusSeat seatForPassenger;
            if(p.getBusSeat() != null && p.getBusSeat().getSeatType() != null) {
                // Handle specific seat type preference
                int assignedSeatForPassenger = rsnpwp.getRandomSeatNumberWithPreference(p.getBusSeat().getSeatType(), true);
                seatForPassenger = new BusSeat();
                seatForPassenger.setBus(busForBooking);
                seatForPassenger.setSeatNumber(assignedSeatForPassenger);
                seatForPassenger.setSeatType(rsnpwp.getSeatTypeFromSeatNumber(assignedSeatForPassenger));
                seatForPassenger.setOccupied(true);
                seatForPassenger.setCreatedAt(ZonedDateTime.now(ZoneId.of("Asia/Kolkata")));
            } else {
                // Handle "NO_PREFERENCE" case, assign any available seat
                seatForPassenger = getBusSeatWithoutPreference(busForBooking);
            }

            busSeatRepository.save(seatForPassenger);

            Float seatCostForUser = seatCostRepository.findCostByBusType(
                    busSeatRepository.findBusTypeFromBusSeat(seatForPassenger));
            bookingCost += seatCostForUser;

            p.setBusSeat(seatForPassenger);
            booking.addPassenger(p);
        }

        booking.setPrice(bookingCost);
        booking.setTravelDate(LocalDate.parse(travelDate));
        booking.setBookingDate(LocalDate.now());
        booking.setBusRoute(busRouteForBooking);

        Payment payment = new Payment();
        payment.setPaymentMethod(null);
        payment.setPaymentStatus(PaymentStatus.PENDING);
        payment.setBooking(booking);
        payment.setAmount(0.00f);
        payment.setPaymentDate(null);
        booking.setPayment(payment);

        busForBooking.setAvailableSeats(busForBooking.getAvailableSeats() -
                booking.getPassengers().size());

        busRepository.save(busForBooking);
        bookingRepository.save(booking);
        
        return ResponseEntity.ok(booking);
    }

    private BusSeat getBusSeatWithoutPreference(Bus busForBooking) {
        RandomSeatNumberProvider rsnp = new RandomSeatNumberProvider(busRepository, busSeatRepository);
        rsnp.setBusNo(busForBooking.getBusNo());

        int assignedSeatForUser = rsnp.getRandomSeatNumber();
        BusSeat busSeatForUser = new BusSeat();
        busSeatForUser.setBus(busForBooking);
        busSeatForUser.setSeatNumber(assignedSeatForUser);
        busSeatForUser.setSeatType(rsnp.getSeatTypeFromSeatNumber(assignedSeatForUser));
        busSeatForUser.setOccupied(true);
        busSeatForUser.setCreatedAt(ZonedDateTime.now(ZoneId.of("Asia/Kolkata")));
        return busSeatForUser;
    }

    public ResponseEntity<?> updateTravelStatus(TravelRequest travelRequest) {
        Optional<Booking> booking = bookingRepository.findById(travelRequest.getBookingId());
        if (booking.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Booking not found"));
        }

        Set<Passenger> passengers = new HashSet<>();

        for(Integer passengerId : travelRequest.getPassengerIds()) {
            Optional<Passenger> passenger = passengerRepository.findById(passengerId);
            passenger.ifPresent(passengers::add);
        }

        for(Passenger p : passengers) {
            p.setTraveled(true);
        }

        return ResponseEntity.ok().body(passengerRepository.saveAll(passengers));
    }

    public Optional<List<Passenger>> findPassengersTraveledOnDate(LocalDate travelDate) {
        return passengerRepository.findPassengersByTravelDate(travelDate);
    }

}
