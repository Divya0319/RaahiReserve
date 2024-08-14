package com.fastturtle.redbusschemadesign.repositories;

import com.fastturtle.redbusschemadesign.models.Booking;
import com.fastturtle.redbusschemadesign.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {

    @Query("SELECT p FROM Payment p WHERE p.booking.bookingId = :bookingId")
    Optional<Payment> findByBookingId(@Param("bookingId") int bookingId);
}
