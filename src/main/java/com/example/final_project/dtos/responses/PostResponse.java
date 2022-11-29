package com.example.final_project.dtos.responses;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class PostResponse {
    Long postId;
    Long accountId;
    Long classroomId;
    String content;
    Timestamp timestamp;
    List<ChildPostResponse> childPosts;
}
