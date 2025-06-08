package com.vata;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class HealthCheckController {

    @GetMapping("/health")
    public String healthCheck() {
        return "OK";
    }
}
