package com.example.final_project.dtos.requests;

import lombok.Data;

@Data
public class RequestRequest {
    Long ownerId;
    Long requesterId;
    Long memberId;
    Long classroomId;
}
