package com.example.APIGateway.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
public class HandleException extends  RuntimeException{

    private final String responseBody;
    private final HttpStatus status;
}
