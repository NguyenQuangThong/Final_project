package com.example.final_project.repositories;

import com.example.final_project.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByUsername(String username);

    List<Account> findByAccountIdIsNotIn(List<Long> ids);

    Account findByEmail(String email);

    List<Account> findAllByRole_RoleId(Long id);

}
