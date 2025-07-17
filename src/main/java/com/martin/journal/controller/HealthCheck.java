package com.martin.journal.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health-check")
public class HealthCheck {
    @GetMapping()
    public ResponseEntity<String> getHealth(){
        return new ResponseEntity<>("Server Running!!", HttpStatus.OK);
    }
}
