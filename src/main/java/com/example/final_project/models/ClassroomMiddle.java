package com.example.final_project.models;

import javax.persistence.*;

@Entity
public class ClassroomMiddle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long classroomMiddleId;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id", nullable = false)
    Account account;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "classroom_id", nullable = false)
    Classroom classroom;
}
