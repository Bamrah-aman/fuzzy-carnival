package com.grabbingthecode.authservice.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/demo")
public class DemoController {

    @GetMapping
    public String get() {
        return "GET :: IF YOU ARE SEEING THIS MESSAGE WE HAVE SUCCESSFULLY CONNECTED AUTH SERVICE VIA GATEWAY";
    }
}
