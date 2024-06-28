package com.example.ojt.service.account;


import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class TokenService {
    private Map<String, String> tokenStorage = new HashMap<>(); // This should be replaced with a persistent storage

    public String generateToken(String email) {
        String token = UUID.randomUUID().toString();
        tokenStorage.put(token, email);
        return token;
    }

    public boolean isValid(String token) {
        return tokenStorage.containsKey(token);
    }

    public String getEmailFromToken(String token) {
        return tokenStorage.get(token);
    }

    public void removeToken(String token) {
        tokenStorage.remove(token);
    }


}
