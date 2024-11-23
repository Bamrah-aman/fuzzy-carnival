package com.grabbingthecode.personaldetailsservice.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/personal-details")
public class DemoController {

    @GetMapping
    public String demoResponse() {
        return "GET :: IF YOU ARE SEEING THIS MESSAGE WE HAVE SUCCESSFULLY PERSONAL DETAILS SERVICE AUTH SERVICE VIA GATEWAY";
    }
}
