package com.georgesalise.apiRepo.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/health")
public class HealthController {
    @GetMapping
    public String healthCheck() {
        System.out.println("Health check endpoint accessed at " + LocalDateTime.now());
        return "API is healthy!";
    }
}
