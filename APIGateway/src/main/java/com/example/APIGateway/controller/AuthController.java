package com.example.APIGateway.controller;

import com.example.APIGateway.filter.JWTFilter;
import com.example.APIGateway.model.AuthCredential;
import com.example.APIGateway.service.FetchTokenService;
import com.example.APIGateway.util.JWTSecretKey;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

   @Autowired
   FetchTokenService fetchTokenService;


    @GetMapping(path = "/token")
    public Mono<String> getToken() {
        return fetchTokenService.callSecuredApi();
    }


}
