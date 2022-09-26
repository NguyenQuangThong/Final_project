package com.example.final_project.models;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long messageId;
    @Column(name = "content", nullable = false, columnDefinition = "varchar(1000)")
    String content;
    @Column(name = "send_at", nullable = false, columnDefinition = "timestamp")
    Timestamp timestamp;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "classroom_id", nullable = false)
    Classroom classroom;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id", nullable = false)
    Account account;
}
