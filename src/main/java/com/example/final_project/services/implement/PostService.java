package com.example.final_project.services.implement;

import com.example.final_project.configures.JwtTokenUtils;
import com.example.final_project.dtos.requests.PostRequest;
import com.example.final_project.dtos.responses.PostResponse;
import com.example.final_project.models.Post;
import com.example.final_project.repositories.AccountRepository;
import com.example.final_project.repositories.ClassroomRepository;
import com.example.final_project.repositories.PostRepository;
import com.example.final_project.services.IPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
    ClassroomRepository classroomRepository;
    @Autowired
    JwtTokenUtils jwtTokenUtils;

    @Override
    public List<PostResponse> getAllPosts() {
        List<Post> posts = postRepository.findAll(Sort.by(Sort.Direction.ASC, "postId"));
        List<PostResponse> result = new ArrayList<>();
        for (Post post : posts)
            result.add(jwtTokenUtils.modelMapper().map(post, PostResponse.class));
        return result;
    }

    @Override
    public void createPost(PostRequest postRequest) {
        Post post = new Post();
        post.setAccount(accountRepository.findById(postRequest.getAccountId()).get());
        post.setClassroom(classroomRepository.findById(postRequest.getClassroomId()).get());
        post.setContent(postRequest.getContent());
        post.setTimestamp(new Timestamp(new Date().getTime()));
        postRepository.save(post);
    }

    @Override
    public PostResponse getPostById(Long id) {
        return jwtTokenUtils.modelMapper().map(postRepository.findById(id).get(), PostResponse.class);
    }

    @Override
    public List<PostResponse> getPostByClassroomId(Long id) {
        List<Post> posts = postRepository.findByClassroom_ClassroomId(id, Sort.by(Sort.Direction.ASC, "postId"));
        List<PostResponse> result = new ArrayList<>();
        for (Post post : posts)
            result.add(jwtTokenUtils.modelMapper().map(post, PostResponse.class));
        return result;
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
