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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    Account roomOwner;
    //    @OneToMany(fetch = FetchType.LAZY, mappedBy = "classroom", cascade = CascadeType.ALL)
//    List<ClassroomMiddle> classroomMiddles;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    List<Account> roomMembers;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "classroom", cascade = CascadeType.ALL)
    List<Post> posts;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "classroom", cascade = CascadeType.ALL)
    List<Message> messages;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "classroom", cascade = CascadeType.ALL)
    List<File> files;
}
