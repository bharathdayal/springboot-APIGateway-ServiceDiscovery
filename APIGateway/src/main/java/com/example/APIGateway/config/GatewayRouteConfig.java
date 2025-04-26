package com.example.APIGateway.config;

import com.example.APIGateway.filter.JWTFilter;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.reactive.error.DefaultErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.result.view.ViewResolver;

import java.util.Objects;
import java.util.stream.Collectors;

@Configuration
public class GatewayRouteConfig {

    @Autowired
    JWTFilter jwtFilter;

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {

        return builder.routes()
                // Order Service Route
                .route("order-service",

                        route -> route
                                .path("/api/order/**")
                                //.filters(f -> f.setPath("/v1/api/order"))
                                //.filters(f -> f.stripPrefix(1))
                                //.filters(f->f.prefixPath("/v1/api/order"))
                                .filters(f -> f.filter(jwtFilter)
                                        .rewritePath("/api/order/v1", "/api/order"))
                                //.filters(f->f.setPath("/v1/order"))
                                .uri("lb://order-service")

                )

                // Product Service Route
                .route("product-service",
                        route -> route
                                .path("/api/product/**")
                                .filters(f -> f.filter(jwtFilter).rewritePath("/api/product/v1", "/api/product"))
                                .uri("lb://product-service")

                )

                .build();

    }




}
