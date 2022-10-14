package com.example.final_project.controllers;

import com.example.final_project.dtos.requests.ChildPostRequest;
import com.example.final_project.dtos.responses.ChildPostResponse;
import com.example.final_project.dtos.responses.MessageResponse;
import com.example.final_project.services.implement.ChildPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/child-posts")
public class ChildPostController {
    @Autowired
    ChildPostService childPostService;

    @GetMapping("")
    public ResponseEntity<List<ChildPostResponse>> getAllChildPosts() {
        return new ResponseEntity<>(childPostService.getAllChildPosts(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChildPostResponse> getChildPostById(@PathVariable Long id) {
        return new ResponseEntity<>(childPostService.getChildPostById(id), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<MessageResponse> createChildPost(@RequestBody ChildPostRequest childPostRequest) {
        childPostService.createChildPost(childPostRequest);
        return new ResponseEntity<>(new MessageResponse("Ok", HttpStatus.OK.value()), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageResponse> updateChildPost(@PathVariable Long id, @RequestBody ChildPostRequest childPostRequest) {
        childPostService.updateChildPost(id, childPostRequest);
        return new ResponseEntity<>(new MessageResponse("Ok", HttpStatus.OK.value()), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteChildPost(@PathVariable Long id) {
        childPostService.deleteChildPost(id);
        return new ResponseEntity<>(new MessageResponse("Ok", HttpStatus.OK.value()), HttpStatus.OK);
    }
}
