package com.example.final_project.dtos.responses;

import lombok.Data;

import java.util.List;

@Data
public class ClassroomResponse {
    Long classroomId;
    String className;
    AccountResponse roomOwner;
    List<AccountResponse> roomMembers;
}
