package com.fastturtle.swiftSeat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SwiftSeatApplication {

    public static void main(String[] args) {
        SpringApplication.run(SwiftSeatApplication.class, args);
    }

}
