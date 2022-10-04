package com.example.final_project.services.implement;

import com.example.final_project.models.Key;
import com.example.final_project.repositories.KeyRepository;
import com.example.final_project.services.IKeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;
import java.util.List;

@Service
public class KeyService implements IKeyService {
    @Autowired
    KeyRepository keyRepository;

    @Override
    public void addNewKeyPair(PublicKey publicKey, PrivateKey privateKey) {
        Key key = new Key();
        key.setPublicKey(Base64.getEncoder().encodeToString(publicKey.getEncoded()));
        key.setPrivateKey(Base64.getEncoder().encodeToString(privateKey.getEncoded()));
        keyRepository.save(key);
    }

    @Override
    public List<Key> getAllKeyPair() {
        return keyRepository.findAll();
    }
}
