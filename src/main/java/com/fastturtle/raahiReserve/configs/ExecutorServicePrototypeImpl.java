package com.fastturtle.raahiReserve.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class ExecutorServicePrototypeImpl implements ExecutorServicePrototype {

    @Bean
    @Scope("prototype")
    public ExecutorService busRouteExecutor() {
        return Executors.newFixedThreadPool(5);
    }

    @Override
    public ExecutorService clone() {
        return null;
    }
}