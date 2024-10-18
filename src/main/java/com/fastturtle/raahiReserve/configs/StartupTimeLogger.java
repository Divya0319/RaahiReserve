package com.fastturtle.raahiReserve.configs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class StartupTimeLogger implements ApplicationListener<ApplicationReadyEvent> {

    private static Logger logger = LogManager.getLogger(StartupTimeLogger.class);
    private final long startTime;

    public StartupTimeLogger() {
        this.startTime = System.currentTimeMillis();
        logger.info("Application initialization started..");
    }
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        long endTime = System.currentTimeMillis();
        long startupTime = endTime - startTime;

        logger.info("Application initialization completed.");
        logger.info("Startup time : {} ms", startupTime);
    }
}
