package com.example.final_project.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class Classroom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long classroomId;
    @Column(name = "class_name", nullable = false, columnDefinition = "varchar(50)")
    String className;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "classroom", cascade = CascadeType.ALL)
    List<ClassroomMiddle> classroomMiddles;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "classroom", cascade = CascadeType.ALL)
    List<Message> messages;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "classroom", cascade = CascadeType.ALL)
    List<File> files;
}
