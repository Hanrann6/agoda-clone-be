package com.efub.agodaclone.user.jwt;

import com.efub.agodaclone.global.exception.ClientExceptionCode;
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
public class JwtProvider { //jwt 발급

    @Value("${jwt.secret-key}")
    private String secretKey;

    private final long expirationMs = 36000000; //10시간

    //사용자의 id를 받아 access token 발급
    public String generateToken(Long userId) {
        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(getKey(), SignatureAlgorithm.HS512) // SecretKey 그대로 사용
                .compact();
    }

    //refresh token 발급용
    public String generateRefreshToken(Long userId) {
        long refreshExpirationMs = 1000L * 60 * 60 * 24 * 14; // 14일
        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshExpirationMs))
                .signWith(getKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    //jwt 서명 검증
    public Long validateAndGetUserId(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(getKey())
                .parseClaimsJws(token)
                .getBody();
        return Long.valueOf(claims.getSubject());
    }

    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

}

