package com.example.final_project.controllers;

import com.example.final_project.dtos.requests.ClassMemberRequest;
import com.example.final_project.dtos.requests.ClassroomRequest;
import com.example.final_project.dtos.responses.ClassroomResponse;
import com.example.final_project.dtos.responses.MessageResponse;
import com.example.final_project.services.implement.ClassroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/classrooms")
public class ClassroomController {
    @Autowired
    ClassroomService classroomService;

    @GetMapping("")
    public ResponseEntity<List<ClassroomResponse>> getAllClassrooms() {
        List<ClassroomResponse> classroomResponses = classroomService.getAllClassrooms();
        return !classroomResponses.isEmpty() ? new ResponseEntity<>(classroomResponses, HttpStatus.OK) : new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassroomResponse> getClassroomById(@PathVariable Long id) {
        ClassroomResponse classroomResponse = classroomService.getClassroomById(id);
        return classroomResponse != null ? new ResponseEntity<>(classroomResponse, HttpStatus.OK) : new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PostMapping("")
    public ResponseEntity<MessageResponse> createClassroom(@RequestBody ClassroomRequest classroomRequest) {
        Boolean check = classroomService.createClassroom(classroomRequest);
        return check ? new ResponseEntity<>(new MessageResponse("Create new class successfully", HttpStatus.OK.value()), HttpStatus.OK) : new ResponseEntity<>(new MessageResponse("Create new class failed", HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageResponse> updateClassroom(@PathVariable Long id, ClassroomRequest classroomRequest) {
        Boolean check = classroomService.updateClassroom(id, classroomRequest);
        return check ? new ResponseEntity<>(new MessageResponse("Update classroom successfully", HttpStatus.OK.value()), HttpStatus.OK) : new ResponseEntity<>(new MessageResponse("Update classroom failed", HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteClassroom(@PathVariable Long id) {
        Boolean check = classroomService.deleleClassroom(id);
        return check ? new ResponseEntity<>(new MessageResponse("Delete classroom successfully", HttpStatus.OK.value()), HttpStatus.OK) : new ResponseEntity<>(new MessageResponse("Update classroom failed", HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/{id}")
    public ResponseEntity<MessageResponse> addClassroomMember(@PathVariable Long id, @RequestBody ClassMemberRequest classMemberRequest) {
        classroomService.addClassroomMember(id, classMemberRequest.getAccountId());
        return new ResponseEntity<>(new MessageResponse("ok", HttpStatus.OK.value()), HttpStatus.OK);
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<MessageResponse> removeClassroomMember(@PathVariable Long id, @RequestBody ClassMemberRequest classMemberRequest) {
        classroomService.removeClassroomMember(id, classMemberRequest.getAccountId());
        return new ResponseEntity<>(new MessageResponse("OK", HttpStatus.OK.value()), HttpStatus.OK);
    }
}
