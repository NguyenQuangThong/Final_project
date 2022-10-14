package com.example.final_project.services;

import com.example.final_project.dtos.requests.PostRequest;
import com.example.final_project.dtos.responses.PostResponse;

import java.util.List;

public interface IPostService {
    List<PostResponse> getAllPosts();

    void createPost(PostRequest postRequest);

    PostResponse getPostById(Long id);

    void editPost(Long id, PostRequest postRequest);

    void deletePost(Long id);
}
