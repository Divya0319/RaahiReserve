package com.fastturtle.redbusschemadesign.controllers;

import com.fastturtle.redbusschemadesign.dtos.TravelRequest;
import com.fastturtle.redbusschemadesign.models.Travel;
import com.fastturtle.redbusschemadesign.services.TravelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/travels")
public class TravelController {

    private final TravelService travelService;

    @Autowired
    public TravelController(TravelService travelService) {
        this.travelService = travelService;
    }

    @PostMapping("/travel")
    public Travel travel(@RequestBody TravelRequest travelRequest) {
        return travelService.travel(travelRequest);
    }

    @GetMapping("/passengersOnDate")
    public long getPassengersTraveledOnDate(@RequestParam LocalDate date) {
        return travelService.getPassengersTraveledOnDate(date);
    }
}
