package com.max.auth.service;

import com.max.auth.tools.JWTUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private JWTUtil jwtUtil;

    public String generateToken(String id, String email, String role) {
        String token = jwtUtil.createJWT(id, email, role);
        return token;
    }

    public Claims verify(String token) {

        try {
            Claims claims = jwtUtil.parseJWT(token);
            return claims;
        } catch (Exception e) {
            return null;
        }
    }



}
