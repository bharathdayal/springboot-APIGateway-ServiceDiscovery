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
   JWTSecretKey secretKey;

   @Autowired
   JWTFilter jwtFilter;

   @Autowired
   FetchTokenService fetchTokenService;

    @PostMapping("/token")
    public ResponseEntity<?> authenticate(@RequestBody AuthCredential authCredential) {

        if ("test".equals(authCredential.getUsername()) && "admin".equals(authCredential.getRole())) {
            String token = Jwts.builder()
                    .subject(authCredential.getUsername())
                    .issuedAt(new Date())
                    .expiration(new Date(System.currentTimeMillis() + 60 * 60 * 1000))
                    .signWith(secretKey.getSignInKey())
                    .compact();
            return ResponseEntity.ok(Map.of("token", token));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
      }


    @GetMapping(path = "/token")
    public Mono<String> getToken() {
        return fetchTokenService.callSecuredApi();
    }

    @GetMapping(path = "/secure1")
    public String test() {
        return "test";
    }

}
