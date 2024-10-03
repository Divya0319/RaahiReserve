package com.fastturtle.raahiReserve.schema_initializers;

import com.fastturtle.raahiReserve.services.InitialDataService;
import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class SampleDataInitializer {

    private final InitialDataService initialDataService;


    @Autowired
    public SampleDataInitializer(InitialDataService initialDataService) {
        this.initialDataService = initialDataService;
    }

    @PostConstruct
    public void init() {

        initialDataService.createAndSaveBusesAndBusRoutes();
        initialDataService.createAndInsert10MoreBuses();
        initialDataService.createAndSaveUsers();

    }

}
