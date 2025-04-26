package com.example.auth_server.controller;

import com.example.auth_server.model.AuthCredential;
import com.example.auth_server.model.JwtResponse;
import com.example.auth_server.util.JWTSecretKey;
import com.example.auth_server.util.RsaKeyUtil;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    JWTSecretKey secretKey;

    @Autowired
    RsaKeyUtil rsaKeyUtil;


    @PostMapping("/token")
    public ResponseEntity<?> authenticate(@RequestBody AuthCredential authCredential) throws Exception {

        // if ("admin".equals(authCredential.getUsername()) && "order".equals(authCredential.getRole())) {
        String token = Jwts.builder()
                .claim("user",authCredential.getUsername())
                .claim("role",authCredential.getRole())
                .subject(authCredential.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 60 * 60 * 1000))
                .signWith(rsaKeyUtil.loadPrivateKey())
                .compact();
        //return ResponseEntity.ok(new JwtResponse(token));
        return ResponseEntity.ok(Map.of("token",token));
        //}
        //return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }
}
