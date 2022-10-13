package com.example.final_project.dtos.requests;

import lombok.Data;

import java.util.List;

@Data
public class ClassMemberRequest {
    List<Long> accountId;
}
