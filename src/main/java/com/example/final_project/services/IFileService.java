package com.example.final_project.services;

import com.example.final_project.dtos.responses.FileResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IFileService {
    void uploadFile(Long accountId, Long classroomId, MultipartFile[] files) throws IOException;

    List<FileResponse> getAllFiles();

    List<FileResponse> getFileByClassroomId(Long id);

    void deleteFile(Long id);
}
