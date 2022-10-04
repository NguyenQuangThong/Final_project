package com.example.final_project.models;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class ChildPost {
    public Long getChildPostId() {
        return childPostId;
    }

    public void setChildPostId(Long childPostId) {
        this.childPostId = childPostId;
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

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long childPostId;
    @Column(name = "child_post_content", nullable = false, columnDefinition = "varchar(1000)")
    String content;
    @Column(name = "post_at", nullable = false, columnDefinition = "timestamp")
    Timestamp timestamp;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id", nullable = false)
    Post post;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id", nullable = false)
    Account account;
}
