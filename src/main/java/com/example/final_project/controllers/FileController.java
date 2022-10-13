package com.example.final_project.controllers;

import com.example.final_project.dtos.responses.FileResponse;
import com.example.final_project.services.implement.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/files")
public class FileController {
    @Autowired
    FileService fileService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public void uploadFile(@RequestParam Long accountId,
                           @RequestParam Long classroomId,
                           @RequestParam MultipartFile file) throws IOException {
        fileService.uploadFile(accountId, classroomId, file);
    }

    @GetMapping("")
    public ResponseEntity<List<FileResponse>> getFile() {
        return new ResponseEntity<>(fileService.getAllFiles(), HttpStatus.OK);
    }
}
