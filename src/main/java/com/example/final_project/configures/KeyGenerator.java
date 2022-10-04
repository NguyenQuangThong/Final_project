package com.example.final_project.configures;

import com.example.final_project.models.Key;
import com.example.final_project.repositories.KeyRepository;
import com.example.final_project.services.implement.KeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
@Component
public class KeyGenerator {
    @Autowired
    KeyRepository keyRepository;
    @Autowired
    KeyService keyService;
    PublicKey publicKey;
    PrivateKey privateKey;
    int keySize = 1024;

    public List<java.security.Key> genKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(keySize);
        KeyPair keyPair = keyPairGenerator.genKeyPair();
        publicKey = keyPair.getPublic();
        privateKey = keyPair.getPrivate();
        keyService.addNewKeyPair(publicKey, privateKey);
        return Arrays.asList(publicKey, privateKey);
    }

    public List<java.security.Key> getKeyPair() throws NoSuchAlgorithmException, InvalidKeySpecException {
        Key key = keyRepository.findById(1L).orElseThrow();
        byte[] pub = Base64.getDecoder().decode(key.getPublicKey());
        byte[] pri = Base64.getDecoder().decode(key.getPrivateKey());
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        privateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(pri));
        publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(pub));
        return Arrays.asList(publicKey, privateKey);
    }
}
