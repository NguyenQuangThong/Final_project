package com.example.final_project.services.implement;

import com.example.final_project.configures.JwtTokenUtils;
import com.example.final_project.dtos.requests.RequestRequest;
import com.example.final_project.dtos.responses.RequestResponse;
import com.example.final_project.models.Request;
import com.example.final_project.repositories.AccountRepository;
import com.example.final_project.repositories.ClassroomRepository;
import com.example.final_project.repositories.RequestRepository;
import com.example.final_project.services.IRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RequestService implements IRequestService {
    @Autowired
    RequestRepository requestRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    ClassroomRepository classroomRepository;
    @Autowired
    JwtTokenUtils jwtTokenUtils;

    @Override
    public List<RequestResponse> getAllRequest() {
        List<Request> requests = requestRepository.findAll();
        List<RequestResponse> result = new ArrayList<>();
        for (Request request : requests)
            result.add(jwtTokenUtils.modelMapper().map(request, RequestResponse.class));
        return result;
    }

    @Override
    public List<RequestResponse> getRequestByOwnerId(Long ownerId) {
        List<Request> requests = requestRepository.getRequestByRoomOwner_AccountId(ownerId);
        List<RequestResponse> result = new ArrayList<>();
        for (Request request : requests)
            result.add(jwtTokenUtils.modelMapper().map(request, RequestResponse.class));
        return result;
    }

    @Override
    public Boolean createRequest(RequestRequest requestRequest) {
        Request request = new Request();
        try {
            request.setRoomOwner(accountRepository.findById(requestRequest.getOwnerId()).get());
            request.setRequester(accountRepository.findById(requestRequest.getRequesterId()).get());
            request.setMember(accountRepository.findById(requestRequest.getMemberId()).get());
            request.setClassroom(classroomRepository.findById(requestRequest.getClassroomId()).get());
            requestRepository.save(request);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Boolean deleteRequest(Long requestId) {
        try {
            requestRepository.deleteById(requestId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


}
