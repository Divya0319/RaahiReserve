package com.fastturtle.raahiReserve.schema_initializers;

import com.fastturtle.raahiReserve.helpers.RandomSeatNumberProviderWithPreference;
import com.fastturtle.raahiReserve.models.*;
import com.fastturtle.raahiReserve.repositories.BusRepository;
import com.fastturtle.raahiReserve.repositories.BusSeatRepository;
import com.fastturtle.raahiReserve.services.InitialDataService;
import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class SampleDataInitializer {

    private final InitialDataService initialDataService;
    private final BusRepository busRepository;
    private final BusSeatRepository busSeatRepository;

    @Autowired
    public SampleDataInitializer(InitialDataService initialDataService, BusRepository busRepository, BusSeatRepository busSeatRepository) {
        this.initialDataService = initialDataService;
        this.busRepository = busRepository;
        this.busSeatRepository = busSeatRepository;
    }

    @PostConstruct
    public void init() {

        initialDataService.createAndSaveBusesAndBusRoutes();

    }


}
