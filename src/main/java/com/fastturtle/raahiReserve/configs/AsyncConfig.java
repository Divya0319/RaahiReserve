package com.fastturtle.raahiReserve.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class AsyncConfig {

    @Bean(name = "taskExecutor")
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);  // Set the minimum number of threads
        executor.setMaxPoolSize(20);   // Set the maximum number of threads
        executor.setQueueCapacity(50); // Set the task queue capacity
        executor.setThreadNamePrefix("BusRoute-"); // Optional: set thread name prefix
        executor.initialize();
        return executor;
    }
}
