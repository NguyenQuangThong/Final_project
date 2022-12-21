package com.example.final_project.models;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long fileId;
    @Column(name = "file_path", nullable = false, columnDefinition = "varchar(100)")
    String filePath;
    @Column(name = "uploaded_at", nullable = false, columnDefinition = "timestamp")
    Timestamp timestamp;
    @Column(name = "size", nullable = false)
    Double size;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classroom_id", nullable = false)
    Classroom classroom;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    Account account;
}
