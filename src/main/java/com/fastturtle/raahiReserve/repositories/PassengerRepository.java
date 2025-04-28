package com.fastturtle.raahiReserve.repositories;

import com.fastturtle.raahiReserve.dtos.CityPassengerCountDTO;
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

    @Query("""
        SELECT new com.fastturtle.raahiReserve.dtos.CityPassengerCountDTO(r.source, COUNT(DISTINCT p.passengerId))
        FROM Passenger p
        JOIN p.bookings b
        JOIN b.busRoute br
        JOIN br.route r
        WHERE r.source IN (:metroCityNames)
        GROUP BY r.source
        """)
    List<CityPassengerCountDTO> countPassengersFromMetroCities(@Param("metroCityNames") List<String> metroCityNames);
}
