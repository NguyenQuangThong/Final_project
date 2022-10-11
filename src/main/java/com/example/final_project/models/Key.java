package com.example.final_project.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Key {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long keyId;
    @Column(name = "public_key", nullable = false, columnDefinition = "varchar(1000)")
    String publicKey;
    @Column(name = "private_key", nullable = false, columnDefinition = "varchar(1000)")
    String privateKey;
}
