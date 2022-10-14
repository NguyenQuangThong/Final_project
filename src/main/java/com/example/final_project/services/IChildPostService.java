package com.example.final_project.services;

import com.example.final_project.dtos.requests.ChildPostRequest;
import com.example.final_project.dtos.responses.ChildPostResponse;

import java.util.List;

public interface IChildPostService {
    List<ChildPostResponse> getAllChildPosts();

    ChildPostResponse getChildPostById(Long id);

    void createChildPost(ChildPostRequest childPostRequest);

    void updateChildPost(Long id, ChildPostRequest childPostRequest);

    void deleteChildPost(Long id);
}
