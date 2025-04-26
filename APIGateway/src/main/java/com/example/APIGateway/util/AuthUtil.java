package com.example.APIGateway.util;

import com.example.APIGateway.config.WebClientConfig;
import com.example.APIGateway.model.AuthCredential;
import com.example.APIGateway.model.AuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class AuthUtil {


    private final WebClient webClient;

    public AuthUtil(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<String> getToken() {
        AuthCredential authCredential  =new AuthCredential("test", "admin");

        return webClient
                .post()
                .uri("/token")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .bodyValue(authCredential)
                .retrieve()
                .bodyToMono(AuthResponse.class)
                .map(AuthResponse::getToken);
                //.map(response -> {
                    // Extract the token from the response body (assuming it's a JSON string)
                   // return response;
               // });
               // .bodyToMono(AuthResponse.class)
                //.map(AuthResponse::getToken);



    }

}
