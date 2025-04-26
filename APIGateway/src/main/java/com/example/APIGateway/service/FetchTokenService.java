package com.example.APIGateway.service;

import com.example.APIGateway.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class FetchTokenService {

    private final WebClient webClient;

    @Autowired
    AuthUtil authUtil;


    public FetchTokenService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<String> callSecuredApi() {

        return authUtil.getToken();

    }
}
