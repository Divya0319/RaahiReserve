package com.fastturtle.redbusschemadesign.controllers;

import com.fastturtle.redbusschemadesign.models.Bus;
import com.fastturtle.redbusschemadesign.services.BusService;
import com.fastturtle.redbusschemadesign.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/booking/bus")
public class BusController {

    private BusService busService;
    private UserService userService;

    @Autowired
    public BusController(BusService busService, UserService userService) {
        this.busService = busService;
        this.userService = userService;
    }

    @GetMapping("/availableOnRoute")
    public List<Bus> getAvailableBusesOnRoute(@RequestParam("source") String source, @RequestParam("destination") String destination) {
        return busService.getAvailableBusesOnRoute(source, destination);
    }
}
