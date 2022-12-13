package com.example.final_project.dtos.responses;

import lombok.Data;

@Data
public class AccountResponse {
    Long accountId;
    String username;
    String fullName;
    String avatar;
    String email;
    String role;
//    List<ClassroomResponse> memberOf;
}
