package com.fastturtle.redbusschemadesign.repositories;

import com.fastturtle.redbusschemadesign.models.Booking;
import com.fastturtle.redbusschemadesign.models.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Integer> {

    Optional<Booking> findByBookingId(int bookingId);

    @Query("SELECT AVG(p.amount) FROM Booking b JOIN b.payment p WHERE b.bookingDate = :date")
    Double findAverageCostOfTicketsOnDate(@Param("date") LocalDate date);

    @Query("SELECT p FROM Booking b JOIN b.passengers p WHERE b = :booking")
    List<Passenger> findAllPassengersInBooking(@Param("booking") Booking booking);

    @Query("SELECT COUNT(p) FROM Booking b JOIN b.passengers p WHERE b.travelDate = :date")
    long countByTraveledDate(@Param("date") LocalDate date);
}
