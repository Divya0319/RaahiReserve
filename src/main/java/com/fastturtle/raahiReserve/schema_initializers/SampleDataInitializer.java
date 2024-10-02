package com.fastturtle.raahiReserve.schema_initializers;

import com.fastturtle.raahiReserve.helpers.RandomSeatNumberProviderWithPreference;
import com.fastturtle.raahiReserve.models.*;
import com.fastturtle.raahiReserve.repositories.BusRepository;
import com.fastturtle.raahiReserve.repositories.BusSeatRepository;
import com.fastturtle.raahiReserve.services.InitialDataService;
import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Component
public class SampleDataInitializer {

    private final InitialDataService initialDataService;
    private final BusRepository busRepository;
    private final BusSeatRepository busSeatRepository;
    private final TaskExecutor taskExecutor;

    @Autowired
    public SampleDataInitializer(InitialDataService initialDataService, BusRepository busRepository, BusSeatRepository busSeatRepository, @Qualifier("taskExecutor") TaskExecutor taskExecutor) {
        this.initialDataService = initialDataService;
        this.busRepository = busRepository;
        this.busSeatRepository = busSeatRepository;
        this.taskExecutor = taskExecutor;
    }

    @PostConstruct
    public void init() {

//        initialDataService.createAndSaveBusesAndBusRoutes();
        taskExecutor.execute(initialDataService::createAndSaveBusesAndBusRoutes);

    }


}
