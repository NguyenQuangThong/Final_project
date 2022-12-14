package com.example.final_project.models;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long accountId;
    @Column(name = "username", nullable = false, columnDefinition = "varchar(50)")
    String username;
    @Column(name = "password", nullable = false, columnDefinition = "varchar(200)")
    String password;
    @Column(name = "full_name", columnDefinition = "varchar(50)")
    String fullName;
    @Column(name = "avatar", columnDefinition = "varchar(200)")
    String avatar;
    @Column(name = "email", columnDefinition = "varchar(100)")
    String email;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    Role role;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "roomOwner", cascade = CascadeType.ALL)
    List<Classroom> ownerOf;
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "roomMembers", cascade = CascadeType.ALL)
    List<Classroom> memberOf;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "account", cascade = CascadeType.ALL)
    List<Post> posts;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "account", cascade = CascadeType.ALL)
    List<ChildPost> childPosts;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "account", cascade = CascadeType.ALL)
    List<Message> messages;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "account", cascade = CascadeType.ALL)
    List<File> files;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "roomOwner", cascade = CascadeType.ALL)
    List<Request> requests;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "requester", cascade = CascadeType.ALL)
    List<Request> requests1;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member", cascade = CascadeType.ALL)
    List<Request> requests2;
}
