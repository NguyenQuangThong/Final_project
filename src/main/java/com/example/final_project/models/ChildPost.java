package com.example.final_project.models;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class ChildPost {
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
