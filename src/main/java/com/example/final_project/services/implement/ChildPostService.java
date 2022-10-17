package com.example.final_project.services.implement;

import com.example.final_project.configures.JwtTokenUtils;
import com.example.final_project.dtos.requests.ChildPostRequest;
import com.example.final_project.dtos.responses.ChildPostResponse;
import com.example.final_project.models.ChildPost;
import com.example.final_project.repositories.AccountRepository;
import com.example.final_project.repositories.ChildPostRepository;
import com.example.final_project.repositories.PostRepository;
import com.example.final_project.services.IChildPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ChildPostService implements IChildPostService {
    @Autowired
    ChildPostRepository childPostRepository;
    @Autowired
    JwtTokenUtils jwtTokenUtils;
    @Autowired
    PostRepository postRepository;
    @Autowired
    AccountRepository accountRepository;

    @Override
    public List<ChildPostResponse> getAllChildPosts() {
        List<ChildPost> childPosts = childPostRepository.findAll();
        List<ChildPostResponse> result = new ArrayList<>();
        for (ChildPost childPost : childPosts)
            result.add(jwtTokenUtils.modelMapper().map(childPost, ChildPostResponse.class));
        return result;
    }

    @Override
    public ChildPostResponse getChildPostById(Long id) {
        return jwtTokenUtils.modelMapper().map(childPostRepository.findById(id).get(), ChildPostResponse.class);
    }

    @Override
    public void createChildPost(ChildPostRequest childPostRequest) {
        ChildPost childPost = new ChildPost();
        childPost.setPost(postRepository.findById(childPostRequest.getPostId()).get());
        childPost.setAccount(accountRepository.findById(childPostRequest.getAccountId()).get());
        childPost.setContent(childPostRequest.getContent());
        childPost.setTimestamp(new Timestamp(new Date().getTime()));
        childPostRepository.save(childPost);
    }

    @Override
    public void updateChildPost(Long id, ChildPostRequest childPostRequest) {
        ChildPost childPost = childPostRepository.findById(id).get();
        childPost.setTimestamp(new Timestamp(new Date().getTime()));
        childPost.setContent(childPostRequest.getContent());
        childPostRepository.save(childPost);
    }

    @Override
    public void deleteChildPost(Long id) {
        childPostRepository.deleteById(id);
    }
}
