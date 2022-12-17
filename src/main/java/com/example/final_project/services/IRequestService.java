package com.example.final_project.services;

import com.example.final_project.dtos.requests.RequestRequest;
import com.example.final_project.dtos.responses.RequestResponse;

import java.util.List;

public interface IRequestService {
    List<RequestResponse> getAllRequest();

    List<RequestResponse> getRequestByOwnerId(Long ownerId);

    Boolean createRequest(RequestRequest requestRequest);

    Boolean deleteRequest(Long requestId);
}
