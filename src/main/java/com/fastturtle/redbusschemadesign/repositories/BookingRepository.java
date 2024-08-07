package com.fastturtle.redbusschemadesign.repositories;

import com.fastturtle.redbusschemadesign.models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface BookingRepository extends JpaRepository<Booking, Integer> {

    @Query("SELECT AVG(p.amount) FROM Booking b JOIN b.payment p WHERE b.bookingDate = :date")
    double findAverageCostOfTicketsOnDate(@Param("date") LocalDate date);
}
