package com.example.final_project.models;

import javax.persistence.*;

@Entity
public class ClassroomMiddle {
    public Long getClassroomMiddleId() {
        return classroomMiddleId;
    }

    public void setClassroomMiddleId(Long classroomMiddleId) {
        this.classroomMiddleId = classroomMiddleId;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Classroom getClassroom() {
        return classroom;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

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
