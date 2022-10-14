package com.example.final_project.services.implement;

import com.example.final_project.configures.JwtTokenUtils;
import com.example.final_project.dtos.requests.PostRequest;
import com.example.final_project.dtos.responses.PostResponse;
import com.example.final_project.models.Post;
import com.example.final_project.repositories.AccountRepository;
import com.example.final_project.repositories.PostRepository;
import com.example.final_project.services.IPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PostService implements IPostService {
    @Autowired
    PostRepository postRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    JwtTokenUtils jwtTokenUtils;

    @Override
    public List<PostResponse> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        List<PostResponse> result = new ArrayList<>();
        for (Post post : posts)
            result.add(jwtTokenUtils.modelMapper().map(post, PostResponse.class));
        return result;
    }

    @Override
    public void createPost(PostRequest postRequest) {
        Post post = new Post();
        post.setAccount(accountRepository.findById(postRequest.getAccountId()).get());
        post.setContent(postRequest.getContent());
        post.setTimestamp(new Timestamp(new Date().getTime()));
        postRepository.save(post);
    }

    @Override
    public PostResponse getPostById(Long id) {
        return jwtTokenUtils.modelMapper().map(postRepository.findById(id).get(), PostResponse.class);
    }

    @Override
    public void editPost(Long id, PostRequest postRequest) {
        Post post = postRepository.findById(id).get();
        post.setContent(postRequest.getContent());
        post.setTimestamp(new Timestamp(new Date().getTime()));
        postRepository.save(post);
    }

    @Override
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}
