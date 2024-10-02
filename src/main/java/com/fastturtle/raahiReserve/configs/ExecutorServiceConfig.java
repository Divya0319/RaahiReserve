package com.fastturtle.raahiReserve.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class ExecutorServiceConfig {

    @Bean(name = "initialDataServiceExecutor")
    public ExecutorService busRouteExecutor() {
        return Executors.newFixedThreadPool(5);
    }

    @Bean(name = "sampleDataInitializerExecutor")
    public ExecutorService anotherServiceExecutor() {
        return Executors.newFixedThreadPool(5);
    }
}