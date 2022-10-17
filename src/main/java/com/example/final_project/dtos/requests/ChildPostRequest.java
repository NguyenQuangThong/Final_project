package com.example.final_project.dtos.requests;

import lombok.Data;

@Data
public class ChildPostRequest {
    String content;
    Long postId;
    Long accountId;
}
