package com.fastturtle.redbusschemadesign.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String initiateLogin() {
        return "login";
    }

}
