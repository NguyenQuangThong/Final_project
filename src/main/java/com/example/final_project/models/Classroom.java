package com.example.final_project.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class Classroom {
    public Long getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(Long classroomId) {
        this.classroomId = classroomId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<ClassroomMiddle> getClassroomMiddles() {
        return classroomMiddles;
    }

    public void setClassroomMiddles(List<ClassroomMiddle> classroomMiddles) {
        this.classroomMiddles = classroomMiddles;
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
