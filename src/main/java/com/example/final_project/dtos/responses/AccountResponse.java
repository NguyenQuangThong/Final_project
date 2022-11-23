package com.example.final_project.dtos.responses;

import lombok.Data;

import java.util.List;

@Data
public class AccountResponse {
    Long accountId;
    String username;
    String fullName;
    String avatar;
    String role;
    List<ClassroomResponse> memberOf;
}
