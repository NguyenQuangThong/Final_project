package com.example.final_project.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long accountId;
    @Column(name = "username", nullable = false, columnDefinition = "varchar(50)")
    String userName;
    @Column(name = "password", nullable = false, columnDefinition = "varchar(50)")
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
}
