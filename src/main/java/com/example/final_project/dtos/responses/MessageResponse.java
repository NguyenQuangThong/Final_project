package com.example.final_project.dtos.responses;

import org.springframework.http.HttpStatus;

public class MessageResponse {
    String message;
    HttpStatus statusCode;

    public MessageResponse() {
    }

    public MessageResponse(String message, HttpStatus statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(HttpStatus statusCode) {
        this.statusCode = statusCode;
    }
}
