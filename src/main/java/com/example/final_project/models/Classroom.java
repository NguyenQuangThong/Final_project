package com.example.final_project.models;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Classroom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long classroomId;

    @Column(name = "class_name", nullable = false, columnDefinition = "varchar(50)")
    String className;

    @Column(name = "class_code", nullable = false, columnDefinition = "varchar(50)")
    String classCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_owner_id", nullable = false)
    Account roomOwner;
    //    @OneToMany(fetch = FetchType.LAZY, mappedBy = "classroom", cascade = CascadeType.ALL)
//    List<ClassroomMiddle> classroomMiddles;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_member_id", nullable = false)
    List<Account> roomMembers;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "classroom", cascade = CascadeType.ALL)
    List<Post> posts;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "classroom", cascade = CascadeType.ALL)
    List<Message> messages;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "classroom", cascade = CascadeType.ALL)
    List<File> files;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "classroom", cascade = CascadeType.ALL)
    List<Request> requests;
}
