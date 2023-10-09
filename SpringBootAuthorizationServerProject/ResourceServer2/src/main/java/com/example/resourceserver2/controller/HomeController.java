package com.example.resourceserver2.controller;

import java.time.LocalDateTime;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        LocalDateTime time = LocalDateTime.now();
        return "Spring Resouce Server 02 - " + time;
    }

}
