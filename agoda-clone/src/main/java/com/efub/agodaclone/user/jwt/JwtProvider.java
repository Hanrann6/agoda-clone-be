package com.efub.agodaclone.user.jwt;

import io.jsonwebtoken.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;

@Component
public class JwtProvider {

    @Value("${jwt.secret-key}")
    private String secretKey;

    private final long expirationMs = 3600000;

    public String generateToken(Long userId) {
        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(getKey(), SignatureAlgorithm.HS512) // SecretKey 그대로 사용
                .compact();
    }

    public Long validateAndGetUserId(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(getKey()) // ✅ 고쳐야 하는 부분!
                    .parseClaimsJws(token)
                    .getBody();
            return Long.valueOf(claims.getSubject());
        } catch (JwtException | IllegalArgumentException e) {
            throw new RuntimeException("유효하지 않은 토큰입니다.", e);
        }
    }

    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    //jwt 키 에러 디버그용..
    @PostConstruct
    public void checkKey() {
        System.out.println("🟢 Loaded secretKey: " + secretKey);
        System.out.println("🧩 Key length: " + secretKey.getBytes(StandardCharsets.UTF_8).length + " bytes");
    }
}

