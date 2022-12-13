package com.example.final_project.dtos.responses;

import lombok.Data;

@Data
public class RequestResponse {
    Long requestId;
    AccountResponse roomOwner;
    AccountResponse requester;
    AccountResponse member;
    ClassroomResponse classroom;
}
