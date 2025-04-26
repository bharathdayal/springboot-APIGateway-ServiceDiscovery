package com.example.APIGateway.util;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

@Component
public class JWTSecretKey {

    private  final String SECRET_KEY = JWTSecretKey.generateSecretKey(32); // Use 256-bit key



    public static String generateSecretKey(int keyLengthBytes) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] keyBytes = new byte[keyLengthBytes];
        secureRandom.nextBytes(keyBytes);
        return Base64.getEncoder().encodeToString(keyBytes);

    }

    public  SecretKey getSignInKey() {
        SecretKey key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(SECRET_KEY));
        byte[] bytes = Base64.getDecoder()
                .decode(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
        return key;
    }


}
