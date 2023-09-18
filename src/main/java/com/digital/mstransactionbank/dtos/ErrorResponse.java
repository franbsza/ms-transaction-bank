package com.digital.mstransactionbank.dtos;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
public class ErrorResponse {
    private HttpStatus statusCode;
    private String message;
    private List<String> errors ;

    public ErrorResponse(HttpStatus statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public ErrorResponse(HttpStatus statusCode, String message, List<String> errors) {
        this.statusCode = statusCode;
        this.message = message;
        this.errors = errors;
    }
}
