package com.example.final_project.dtos.requests;

import lombok.Data;

import java.util.List;

@Data
public class AccountDelete {
    List<Long> ids;
}
