package com.example.final_project.dtos.requests;

import lombok.Data;

@Data
public class PostRequest {
    String content;
    Long accountId;
    Long classroomId;
}
