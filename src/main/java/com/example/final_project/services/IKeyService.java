package com.example.final_project.services;

import com.example.final_project.models.Key;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.List;

public interface IKeyService {
     void addNewKeyPair(PublicKey publicKey, PrivateKey privateKey);
     List<Key> getAllKeyPair();
}
