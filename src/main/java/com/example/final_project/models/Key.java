package com.example.final_project.models;

import javax.persistence.*;

@Entity
public class Key {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long keyId;
    @Column(name = "public_key", nullable = false, columnDefinition = "varchar(1000)")
    String publicKey;
    @Column(name = "private_key", nullable = false, columnDefinition = "varchar(1000)")
    String privateKey;

    public Long getKeyId() {
        return keyId;
    }

    public void setKeyId(Long keyId) {
        this.keyId = keyId;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }
}
