package com.fastturtle.redbusschemadesign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RedBusSchemaDesignApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedBusSchemaDesignApplication.class, args);
    }

}
