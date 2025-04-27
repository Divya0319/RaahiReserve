package com.fastturtle.raahiReserve.repositories;

import com.fastturtle.raahiReserve.models.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PassengerRepository extends JpaRepository<Passenger, Integer> {

    @Query("SELECT p FROM Booking b JOIN b.passengers p WHERE b.travelDateTime = :travelDateTime")
    Optional<List<Passenger>> findPassengersByTravelDate(@Param("travelDateTime") LocalDateTime travelDateTime);
}
