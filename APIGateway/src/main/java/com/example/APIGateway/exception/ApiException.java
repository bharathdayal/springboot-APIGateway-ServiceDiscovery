package com.example.APIGateway.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ApiException extends  RuntimeException {


    private final String error;
    private final String message;
    private final HttpStatus status;
    private LocalDateTime timestamp;


}
