package com.example.final_project.controllers;

import com.example.final_project.dtos.requests.RequestRequest;
import com.example.final_project.dtos.responses.RequestResponse;
import com.example.final_project.services.implement.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/requests")
public class RequestController {
    @Autowired
    RequestService requestService;

    @GetMapping("")
    public ResponseEntity<List<RequestResponse>> getAllRequests() {
        return new ResponseEntity<>(requestService.getAllRequest(), HttpStatus.OK);
    }

    @GetMapping("/room-owner/{id}")
    public ResponseEntity<List<RequestResponse>> getRequestByRoomOwnerId(@PathVariable Long id) {
        return new ResponseEntity<>(requestService.getRequestByOwnerId(id), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Boolean> createRequest(@RequestBody RequestRequest requestRequest) {
        return new ResponseEntity<>(requestService.createRequest(requestRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteRequest(@PathVariable Long id) {
        return new ResponseEntity<>(requestService.deleteRequest(id), HttpStatus.OK);
    }

}
