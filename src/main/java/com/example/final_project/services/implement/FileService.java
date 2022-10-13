package com.example.final_project.services.implement;

import com.example.final_project.configures.JwtTokenUtils;
import com.example.final_project.dtos.responses.FileResponse;
import com.example.final_project.models.File;
import com.example.final_project.repositories.AccountRepository;
import com.example.final_project.repositories.ClassroomRepository;
import com.example.final_project.repositories.FileRepository;
import com.example.final_project.services.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class FileService implements IFileService {
    private static final Path CURRENT_FOLDER = Paths.get(System.getProperty("user.dir"));
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    ClassroomRepository classroomRepository;
    @Autowired
    FileRepository fileRepository;
    @Autowired
    JwtTokenUtils jwtTokenUtils;

    @Override
    public void uploadFile(Long accountId,
                           Long classroomId,
                           MultipartFile file) throws IOException {
        Path staticPath = Paths.get("static");
        Path filePath = Paths.get("files");
        if (!Files.exists(CURRENT_FOLDER.resolve(staticPath).resolve(filePath))) {
            Files.createDirectories(CURRENT_FOLDER.resolve(staticPath).resolve(filePath));
        }
        Path path = CURRENT_FOLDER.resolve(staticPath)
                .resolve(filePath).resolve(file.getOriginalFilename());
        try (OutputStream os = Files.newOutputStream(path)) {
            os.write(file.getBytes());
        }
        File uploadedFile = new File();
        uploadedFile.setFilePath(filePath.resolve(file.getOriginalFilename()).toString());
        uploadedFile.setTimestamp(new Timestamp(new Date().getTime()));
        uploadedFile.setClassroom(classroomRepository.findById(classroomId).get());
        uploadedFile.setAccount(accountRepository.findById(accountId).get());
        fileRepository.save(uploadedFile);
    }

    @Override
    public List<FileResponse> getAllFiles() {
        List<FileResponse> result = new ArrayList<>();
        List<File> files = fileRepository.findAll();
        for (File file : files)
            result.add(jwtTokenUtils.modelMapper().map(file, FileResponse.class));
        return result;
    }
}
