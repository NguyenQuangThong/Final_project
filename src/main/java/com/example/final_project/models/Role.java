package com.example.final_project.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class Role {
    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long roleId;
    @Column(name = "role_name", nullable = false, columnDefinition = "varchar(50)")
    String roleName;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "role", cascade = CascadeType.ALL)
    List<Account> accounts;

    public Role(Long roleId) {
        this.roleId = roleId;
    }

    public Role() {
    }
}
