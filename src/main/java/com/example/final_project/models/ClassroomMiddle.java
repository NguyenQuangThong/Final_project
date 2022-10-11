//package com.example.final_project.models;
//
//import lombok.Data;
//
//import javax.persistence.*;
//
//@Entity
//@Data
//public class ClassroomMiddle {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    Long classroomMiddleId;
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "account_id", nullable = false)
//    Account accountResponse;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "classroom_id", nullable = false)
//    Classroom classroom;
//}
