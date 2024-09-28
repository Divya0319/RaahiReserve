package com.fastturtle.raahiReserve;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RaahiReserveApplication {

    public static void main(String[] args) {
        SpringApplication.run(RaahiReserveApplication.class, args);
    }

}
