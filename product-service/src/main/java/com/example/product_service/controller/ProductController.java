package com.example.product_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @GetMapping
    public String getProduct() {
        return "PRODUCT Service registered with Eureka Service Discovery";
    }

    @GetMapping(path ="/name")
    public ResponseEntity<String> getSubProduct(@RequestParam String productName) {
        return new ResponseEntity<>(productName, HttpStatus.OK);
    }

}
