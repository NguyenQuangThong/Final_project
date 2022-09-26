package com.example.final_project.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long roleId;
    @Column(name = "role_name", nullable = false, columnDefinition = "varchar(50)")
    String roleName;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "role", cascade = CascadeType.ALL)
    List<Account> accounts;
}
