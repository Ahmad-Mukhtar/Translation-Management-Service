package com.digitaltol.translation.security;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${security.jwt.secret}")
    private String secret;
    @Value("${security.jwt.expiration}")
    private long expiration;

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public String validateToken(String token) {
        return Jwts.parser().setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody().getSubject();
    }
}