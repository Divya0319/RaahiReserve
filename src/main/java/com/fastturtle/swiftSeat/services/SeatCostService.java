package com.fastturtle.swiftSeat.services;

import com.fastturtle.swiftSeat.enums.BusType;
import com.fastturtle.swiftSeat.models.SeatCost;
import com.fastturtle.swiftSeat.repositories.SeatCostRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SeatCostService {

    private final SeatCostRepository seatCostRepository;

    public SeatCostService(SeatCostRepository seatCostRepository) {
        this.seatCostRepository = seatCostRepository;
    }

    public Map<BusType, List<SeatCost>> getAllSeatCostsGroupedByBusType() {
        List<SeatCost> seatCosts = seatCostRepository.findAll();
        return seatCosts.stream().collect(Collectors.groupingBy(SeatCost::getBusType));
    }
}
