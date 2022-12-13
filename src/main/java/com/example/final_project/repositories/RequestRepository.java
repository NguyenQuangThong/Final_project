package com.example.final_project.repositories;

import com.example.final_project.models.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
    List<Request> getRequestByRoomOwner_AccountId(Long id);
}
