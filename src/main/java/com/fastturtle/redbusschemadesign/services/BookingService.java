package com.fastturtle.redbusschemadesign.services;

import com.fastturtle.redbusschemadesign.dtos.BookingRequest;
import com.fastturtle.redbusschemadesign.helpers.RandomSeatNumberProvider;
import com.fastturtle.redbusschemadesign.helpers.RandomSeatNumberProviderWithPreference;
import com.fastturtle.redbusschemadesign.models.*;
import com.fastturtle.redbusschemadesign.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;

    private final BusRouteRepository busRouteRepository;

    private final UserRepository userRepository;

    private final BusRepository busRepository;
    private final BusSeatRepository busSeatRepository;
    private final SeatCostRepository seatCostRepository;

    @Autowired
    public BookingService(BookingRepository bookingRepository, BusRouteRepository busRouteRepository, UserRepository userRepository, BusRepository busRepository, BusSeatRepository busSeatRepository, SeatCostRepository seatCostRepository) {
        this.bookingRepository = bookingRepository;
        this.busRouteRepository = busRouteRepository;
        this.userRepository = userRepository;
        this.busRepository = busRepository;
        this.busSeatRepository = busSeatRepository;
        this.seatCostRepository = seatCostRepository;
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

    public ResponseEntity<?> getAverageCostOfTicketsOnDate(LocalDate date) {
        Double averageCost = bookingRepository.findAverageCostOfTicketsOnDate(date);
        ResponseEntity<?> response;
        if(averageCost == null) {
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "No bookings for given date"));
        } else {
            response = ResponseEntity.ok(averageCost);
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

    @Transactional
    public ResponseEntity<?> doBookingFromPassengerForm(Integer userId, String source, String destination, List<Passenger> passengers) {
        if(source.equals(destination)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Source and destination cannot be same"));
        }

        Booking booking = new Booking();
        Float bookingCost = 0.0f;

        List<Bus> busesForBooking = busRepository.findAvailableBusesBySourceAndDestination(source, destination);
        if(busesForBooking.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "No buses found for given source and destination"));
        }

        Bus busForBooking = busesForBooking.get(0);
        BusRoute busRouteForBooking = busRouteRepository.
                findFirstBusRouteBySourceAndDestination(source, destination);

        if (userId != null) {
            User user = userRepository.findById(userId).orElse(null);
            booking.setUser(user);
            booking.setUserPassenger(true);

            BusSeat busSeatForUser = getBusSeat(busForBooking);
            busSeatRepository.save(busSeatForUser);

            BusType busTypeForUser = busSeatRepository.findBusTypeFromBusSeat(busSeatForUser);
            Float seatCostForUser = seatCostRepository.findCostByBusType(busTypeForUser);

            bookingCost += seatCostForUser;

            Passenger userPassenger = new Passenger();
            userPassenger.setName(booking.getUser().getUserName());
            userPassenger.setAge(booking.getUser().getAge());
            userPassenger.setGender(booking.getUser().getGender());
            userPassenger.setBusSeat(busSeatForUser);
            booking.addPassenger(userPassenger);

        } else {
            booking.setUserPassenger(false);
        }

        RandomSeatNumberProviderWithPreference rsnpwp = new RandomSeatNumberProviderWithPreference(busRepository, busSeatRepository);
        rsnpwp.setBusNo(busForBooking.getBusNo());

        for(Passenger p : passengers) {
            int assignedSeatForPassenger = rsnpwp.getRandomSeatNumberWithPreference(p.getBusSeat().getSeatType(), true);
            BusSeat busSeatForPassenger = new BusSeat();
            busSeatForPassenger.setBus(busForBooking);
            busSeatForPassenger.setSeatNumber(assignedSeatForPassenger);
            busSeatForPassenger.setSeatType(rsnpwp.getSeatTypeFromSeatNumber(assignedSeatForPassenger));
            busSeatForPassenger.setOccupied(true);
            busSeatRepository.save(busSeatForPassenger);

            Float seatCostForUser = seatCostRepository.findCostByBusType(
                    busSeatRepository.findBusTypeFromBusSeat(busSeatForPassenger));
            bookingCost += seatCostForUser;

            p.setBusSeat(busSeatForPassenger);
            booking.addPassenger(p);
        }

        booking.setPrice(bookingCost);
        booking.setBookingDate(LocalDate.now());
        booking.setBusRoute(busRouteForBooking);

        Payment payment = new Payment();
        payment.setPaymentMethod(null);
        payment.setPaymentStatus(PaymentStatus.PENDING);
        payment.setBooking(booking);
        payment.setAmount(0.00f);
        payment.setPaymentDate(null);
        booking.setPayment(payment);

        bookingRepository.save(booking);
        
        return ResponseEntity.ok(booking);
    }

    private BusSeat getBusSeat(Bus busForBooking) {
        RandomSeatNumberProvider rsnp = new RandomSeatNumberProvider(busRepository, busSeatRepository);
        rsnp.setBusNo(busForBooking.getBusNo());

        int assignedSeatForUser = rsnp.getRandomSeatNumber();
        BusSeat busSeatForUser = new BusSeat();
        busSeatForUser.setBus(busForBooking);
        busSeatForUser.setSeatNumber(assignedSeatForUser);
        busSeatForUser.setSeatType(rsnp.getSeatTypeFromSeatNumber(assignedSeatForUser));
        busSeatForUser.setOccupied(true);
        return busSeatForUser;
    }
}
