package com.fastturtle.redbusschemadesign.repositories;

import com.fastturtle.redbusschemadesign.models.BusRoute;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusRouteRepository extends JpaRepository<BusRoute, Integer> {
}
