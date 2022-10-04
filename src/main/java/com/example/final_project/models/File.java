package com.example.final_project.models;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class File {
    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Classroom getClassroom() {
        return classroom;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long fileId;
    @Column(name = "file_path", nullable = false, columnDefinition = "varchar(100)")
    String filePath;
    @Column(name = "uploaded_at", nullable = false, columnDefinition = "timestamp")
    Timestamp timestamp;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "classroom_id", nullable = false)
    Classroom classroom;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id", nullable = false)
    Account account;
}
