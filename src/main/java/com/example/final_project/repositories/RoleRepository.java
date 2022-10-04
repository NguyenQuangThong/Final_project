package com.example.final_project.repositories;

import com.example.final_project.models.Account;
import com.example.final_project.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findRoleByAccounts(Account account);
}
