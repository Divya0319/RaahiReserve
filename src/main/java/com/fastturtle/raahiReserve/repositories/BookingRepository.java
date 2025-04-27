package com.fastturtle.raahiReserve.repositories;

import com.fastturtle.raahiReserve.enums.BookingStatus;
import com.fastturtle.raahiReserve.enums.PaymentStatus;
import com.fastturtle.raahiReserve.models.Booking;
import com.fastturtle.raahiReserve.models.Bus;
import com.fastturtle.raahiReserve.models.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Integer> {

    Optional<Booking> findByBookingId(int bookingId);

    @Query("SELECT AVG(p.amount) FROM Booking b JOIN b.payment p WHERE b.bookingDateTime = :date")
    Double findAverageCostOfTicketsOnDate(@Param("date") LocalDate date);

    @Query("SELECT p FROM Booking b JOIN b.passengers p WHERE b = :booking")
    List<Passenger> findAllPassengersInBooking(@Param("booking") Booking booking);

    @Query("SELECT COUNT(p) FROM Booking b JOIN b.passengers p WHERE b.travelDateTime = :date")
    long countByTraveledDate(@Param("date") LocalDate date);

    @Query("SELECT bus FROM Booking b JOIN b.busRoute.bus bus WHERE b.bookingId = :bookingId")
    Bus findBusForBooking(@Param("bookingId") int bookingId);

    @Query("SELECT b FROM Booking b " +
            "JOIN b.payment p " +
            "WHERE p.paymentStatus IN (:pendingStatus, :failedStatus) " +
            "AND :currentDateTime < b.travelDateTime")
    List<Booking> findBookingsWithPendingOrFailedPayments(
            @Param("pendingStatus") PaymentStatus pendingStatus,
            @Param("failedStatus") PaymentStatus failedStatus,
            @Param("currentDateTime") LocalDateTime currentDateTime);

    long count();

    long countByBookingStatus(BookingStatus bookingStatus);
}
