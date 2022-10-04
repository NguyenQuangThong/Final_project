package com.example.final_project.models;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
public class Post {
    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public List<ChildPost> getChildPosts() {
        return childPosts;
    }

    public void setChildPosts(List<ChildPost> childPosts) {
        this.childPosts = childPosts;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long postId;
    @Column(name = "content", columnDefinition = "varchar(1000)")
    String content;
    @Column(name = "created_at", columnDefinition = "timestamp")
    Timestamp timestamp;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id", nullable = false)
    Account account;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post", cascade = CascadeType.ALL)
    List<ChildPost> childPosts;
}
