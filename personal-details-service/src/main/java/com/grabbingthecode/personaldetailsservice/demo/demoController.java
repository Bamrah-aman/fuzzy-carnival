package com.grabbingthecode.personaldetailsservice.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/personal-details")
public class demoController {

    @GetMapping
    public String demoResponse() {
        return "GET :: RESPONSE FROM DEMO CONTROLLER INSIDE PERSONAL-DETAILS-SERVICE";
    }
}
