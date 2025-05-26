package com.efub.agodaclone.user.service;

import com.efub.agodaclone.user.domain.RefreshToken;
import com.efub.agodaclone.user.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    // refresh token을 db에 갱신
    @Transactional
    public void saveOrUpdate(Long userId, String token, LocalDateTime expiration) {
        refreshTokenRepository.deleteByUserId(userId); //기존 토큰 삭제

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUserId(userId);
        refreshToken.setToken(token);
        refreshToken.setExpiration(expiration);

        refreshTokenRepository.save(refreshToken);
    }

}
