package com.example.final_project.services;

import com.example.final_project.dtos.requests.ClassroomRequest;
import com.example.final_project.dtos.responses.ClassroomResponse;

import java.util.List;

public interface IClassroomService {
    Boolean createClassroom(ClassroomRequest classroomRequest);

    List<ClassroomResponse> getAllClassrooms();

    ClassroomResponse getClassroomById(Long id);

    Boolean updateClassroom(Long id, ClassroomRequest classroomRequest);

    Boolean deleleClassroom(Long id);

    void addClassroomMember(Long classroomId, List<Long> accountId);

    void removeClassroomMember(Long classroomId, List<Long> accountId);
}
