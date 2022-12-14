package com.example.final_project.dtos.responses;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class FileResponse {
    Long fileId;
    String filePath;
    Timestamp timestamp;
    Double size;
    ClassroomResponse classroom;
    AccountResponse account;
}
