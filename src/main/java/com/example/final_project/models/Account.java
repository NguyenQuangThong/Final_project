package com.example.final_project.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long accountId;
    @Column(name = "username", nullable = false, columnDefinition = "varchar(50)")
    String username;
    @Column(name = "password", nullable = false, columnDefinition = "varchar(200)")
    String password;
    @Column(name = "full_name", columnDefinition = "varchar(50)")
    String fullName;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "role_id", nullable = false)
    Role role;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "account", cascade = CascadeType.ALL)
    List<ClassroomMiddle> classroomMiddles;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "account", cascade = CascadeType.ALL)
    List<Post> posts;
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "account", cascade = CascadeType.ALL)
    ChildPost childPost;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "account", cascade = CascadeType.ALL)
    List<Message> messages;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "account", cascade = CascadeType.ALL)
    List<File> files;

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<ClassroomMiddle> getClassroomMiddles() {
        return classroomMiddles;
    }

    public void setClassroomMiddles(List<ClassroomMiddle> classroomMiddles) {
        this.classroomMiddles = classroomMiddles;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public ChildPost getChildPost() {
        return childPost;
    }

    public void setChildPost(ChildPost childPost) {
        this.childPost = childPost;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }
}
