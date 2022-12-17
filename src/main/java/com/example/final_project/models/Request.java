package com.example.final_project.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long requestId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_owner_id", nullable = false)
    Account roomOwner;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requester_id", nullable = false)
    Account requester;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    Account member;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classroom_id", nullable = false)
    Classroom classroom;
}
