package com.fastturtle.raahiReserve.repositories;

import com.fastturtle.raahiReserve.models.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RouteRepository extends JpaRepository<Route, Integer> {
    @Query("SELECT r FROM Route r WHERE r.source = :source AND r.destination = :destination")
    Route findBySourceAndDestination(@Param("source") String source, @Param("destination") String destination);


    @Query("SELECT DISTINCT r.source FROM Route r")
    List<String> findDistinctBySource();

    @Query("SELECT DISTINCT r.destination FROM Route r")
    List<String> findDistinctByDestination();
}
