package com.grpc.grpc.auth.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {

    public String generateToken(String username) {
        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + Constants.EXPIRATION_TIME))
            .signWith(SignatureAlgorithm.HS256, Constants.JWT_SIGNING_KEY)
            .compact();
    }
}
