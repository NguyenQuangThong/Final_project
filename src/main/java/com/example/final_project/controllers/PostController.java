package com.example.final_project.controllers;

import com.example.final_project.dtos.requests.PostRequest;
import com.example.final_project.dtos.responses.MessageResponse;
import com.example.final_project.dtos.responses.PostResponse;
import com.example.final_project.services.implement.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {
    @Autowired
    PostService postService;

    @GetMapping("")
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        return new ResponseEntity<>(postService.getAllPosts(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPostById(@PathVariable Long id) {
        return new ResponseEntity<>(postService.getPostById(id), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<MessageResponse> createPost(@RequestBody PostRequest postRequest) {
        postService.createPost(postRequest);
        return new ResponseEntity<>(new MessageResponse("Ok", HttpStatus.OK.value()), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageResponse> editPost(@PathVariable Long id, @RequestBody PostRequest postRequest) {
        postService.editPost(id, postRequest);
        return new ResponseEntity<>(new MessageResponse("Ok", HttpStatus.OK.value()), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return new ResponseEntity<>(new MessageResponse("Ok", HttpStatus.OK.value()), HttpStatus.OK);
    }
}
