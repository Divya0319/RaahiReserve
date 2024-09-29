package com.fastturtle.raahiReserve.controllers;

import com.fastturtle.raahiReserve.enums.BusType;
import com.fastturtle.raahiReserve.services.BusService;
import com.fastturtle.raahiReserve.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
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
    public ResponseEntity<?> getAvailableBusesOnRoute(@RequestParam("source") String source, @RequestParam("destination") String destination) {
        return busService.getAvailableBusesOnRoute(source, destination);
    }

    @GetMapping("/selectBusType")
    public String showBusTypeSelectionForm(Model model) {
        model.addAttribute("busTypes", BusType.values());
        return "selectBusType";
    }

    @PostMapping("/saveSelectedBusType")
    public String saveSelectedBusType(@RequestParam("busType") String selectedBusType, Model model) {
        System.out.println("Selected Bus Type: " + selectedBusType);

        // Add the selected bus type back to the model if you want to display it on a new page.
        model.addAttribute("selectedBusType", selectedBusType);

        // Redirect or return a view name to avoid template resolution error.
        return "bookingSummary"; // Change "result" to the name of your success page template.
    }
}
