package com.example.final_project.dtos.responses;

import lombok.Data;

@Data
public class MessageResponse {
    String message;
    int statusCode;

    public MessageResponse(String message, int statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }
}
