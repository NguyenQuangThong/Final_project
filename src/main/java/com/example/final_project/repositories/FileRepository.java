package com.example.final_project.repositories;

import com.example.final_project.models.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {
    List<File> getFilesByClassroom_ClassroomId(Long id);
}
