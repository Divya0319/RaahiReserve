package com.fastturtle.redbusschemadesign.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class SwaggerRedirectController {

    @GetMapping("/platformRedBus/swagger-ui")
    public RedirectView redirectToSwaggerUI() {
        return new RedirectView("/platformRedBus/swagger-ui/index.html#");
    }

    @GetMapping("/")
    public RedirectView redirectToSwaggerUIFromRoot() {
        return new RedirectView("/platformRedBus/swagger-ui/index.html#");
    }
}
