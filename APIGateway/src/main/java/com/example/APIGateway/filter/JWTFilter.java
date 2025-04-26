package com.example.APIGateway.filter;

import com.example.APIGateway.exception.ApiException;
import com.example.APIGateway.exception.GlobalExceptionHandler;
import com.example.APIGateway.exception.HandleException;
import com.example.APIGateway.model.AuthResponse;
import com.example.APIGateway.service.FetchTokenService;
import com.example.APIGateway.util.AuthUtil;
import com.example.APIGateway.util.JWTSecretKey;
import com.example.APIGateway.util.RsaKeyUtil;
import feign.FeignException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import io.jsonwebtoken.Jwts;


import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;

@Component
public class JWTFilter implements GatewayFilter {

    @Autowired
    JWTSecretKey secretKey;

    @Autowired
    AuthUtil authUtil;

    @Autowired
    FetchTokenService fetchTokenService;

    @Autowired
    RsaKeyUtil rsaKeyUtil;

    private final WebClient webClient;

    public JWTFilter(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {


        ServerHttpRequest request = exchange.getRequest();
        if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
            return this.onError(exchange, "Missing Authorization", HttpStatus.UNAUTHORIZED);
        }

        String token = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        assert token != null;
        token = token.replace("Bearer ", "");

        try {

          Claims claims= Jwts.parser()
                    .verifyWith(rsaKeyUtil.loadPublicKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

        }  catch (Exception e) {
            return Mono.error(new ApiException(e.getCause().toString(), e.getMessage(), HttpStatus.UNAUTHORIZED, LocalDateTime.now()));
           // return this.onError2(exchange, "Invalid Token", HttpStatus.UNAUTHORIZED,e).then();

        }

        return chain.filter(exchange);
    }


    //@Override
    public Mono<Void> filter1(ServerWebExchange exchange, GatewayFilterChain chain) {


        ServerHttpRequest request = exchange.getRequest();
        if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
            return this.onError(exchange, "Missing Authorization", HttpStatus.UNAUTHORIZED);
        }

       // String token = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
       // assert token != null;
        //token = token.replace("Bearer ", "");
        try {
                fetchTokenService.callSecuredApi().subscribe(
                        token -> {
                            // Handle the token here
                                Jwts.parser()
                                        .verifyWith(secretKey.getSignInKey())
                                        .build()
                                        .parseSignedClaims(token)
                                        .getPayload();

                        },
                        error -> {
                            // Handle the error here
                            System.err.println("Error fetching token: " + error.getMessage());
                        }
                );
        } catch (Exception e) {

            return this.onError(exchange, "Invalid Token", HttpStatus.UNAUTHORIZED);

        }


        return chain.filter(exchange);
      }

    private Mono<Void> onError(ServerWebExchange exchange, String message, HttpStatus status) {
        exchange.getResponse().setStatusCode(status);
        return exchange.getResponse().setComplete();
    }

    public Mono<String> callSecuredApi() {
        return authUtil.getToken()
                .flatMap(token -> webClient
                        .get()
                        .uri("/secure/data")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .retrieve()
                        .bodyToMono(String.class)
                );
    }

    private Mono<String> getResource(String authToken) {
        return webClient.get()
                .uri("/auth/resource")
                .headers(h -> h.setBearerAuth(authToken))
                .retrieve()
                .bodyToMono(String.class);
    }
    public Mono<String> obtainSecuredResource() {
        return authUtil.getToken()
                .flatMap(this::getResource);
    }
}
