package com.example.final_project.dtos.responses;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ChildPostResponse {
    Long childPostId;
    String content;
    Timestamp timestamp;
    Long postId;
    AccountResponse account;
}
