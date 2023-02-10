package com.example.final_project.services.implement;

import com.example.final_project.configures.JwtTokenUtils;
import com.example.final_project.dtos.requests.ClassroomRequest;
import com.example.final_project.dtos.responses.ClassroomResponse;
import com.example.final_project.models.Account;
import com.example.final_project.models.Classroom;
import com.example.final_project.repositories.AccountRepository;
import com.example.final_project.repositories.ClassroomRepository;
import com.example.final_project.services.IClassroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class ClassroomService implements IClassroomService {
    @Autowired
    ClassroomRepository classroomRepository;
    @Autowired
    JwtTokenUtils jwtTokenUtils;
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AccountService accountService;

    @Override
    public Boolean createClassroom(ClassroomRequest classroomRequest) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Classroom classroom = new Classroom();
        classroom.setClassName(classroomRequest.getClassName());
        classroom.setRoomOwner(accountRepository.findByUsername(auth.getName()));
        classroom.setClassCode(accountService.getAlphaNumericString(6));
//        classroom.setRoomMembers(Collections.singletonList(accountRepository.findByUsername(auth.getName())));
        classroomRepository.save(classroom);
        return true;
    }

    @Override
    public List<ClassroomResponse> getAllClassrooms() {
        List<ClassroomResponse> result = new ArrayList<>();
        try {
            List<Classroom> classrooms = classroomRepository.findAll();
            for (Classroom classroom : classrooms)
                result.add(jwtTokenUtils.modelMapper().map(classroom, ClassroomResponse.class));
            return result;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public ClassroomResponse getClassroomById(Long id) {
        try {
            return jwtTokenUtils.modelMapper().map(classroomRepository.findById(id).get(), ClassroomResponse.class);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Boolean updateClassroom(Long id, ClassroomRequest classroomRequest) {
        try {
            Classroom classroom = classroomRepository.findById(id).get();
            classroom.setClassName(classroomRequest.getClassName());
            classroomRepository.save(classroom);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Boolean deleleClassroom(Long id) {
        try {
            classroomRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void addClassroomMember(Long classroomId, List<Long> accountId) {
        Classroom classroom = classroomRepository.findById(classroomId).get();
        HashSet<Account> temp = new HashSet<>(classroom.getRoomMembers());
        for (Long aLong : accountId) temp.add(accountRepository.findById(aLong).get());
        classroom.setRoomMembers(new ArrayList<>(temp));
        classroomRepository.save(classroom);
    }

    @Override
    public void removeClassroomMember(Long classroomId, List<Long> accountId) {
        Classroom classroom = classroomRepository.findById(classroomId).get();
        for (Long aLong : accountId) classroom.getRoomMembers().remove(accountRepository.findById(aLong).get());
        classroomRepository.save(classroom);
    }

    @Override
    public Boolean joinClassroom(String code, Long accountId) {
        Classroom find = classroomRepository.findClassroomByClassCode(code);
        if (find != null) {
            Classroom classroom = classroomRepository.findById(find.getClassroomId()).get();
            if(classroom.getRoomOwner().getAccountId().equals(accountId))
                return false;
            classroom.getRoomMembers().add(accountRepository.findById(accountId).get());
            classroomRepository.save(classroom);
            return true;
        } else return false;
    }
}
