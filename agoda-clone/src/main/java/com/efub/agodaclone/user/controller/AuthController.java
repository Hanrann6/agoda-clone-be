package com.efub.agodaclone.user.controller;

import com.efub.agodaclone.global.exception.AgodaException;
import com.efub.agodaclone.global.exception.ExceptionCode;
import com.efub.agodaclone.user.domain.RefreshToken;
import com.efub.agodaclone.user.dto.KakaoUserResponseDto;
import com.efub.agodaclone.user.domain.User;
import com.efub.agodaclone.user.jwt.JwtProvider;
import com.efub.agodaclone.user.jwt.TokenUtil;
import com.efub.agodaclone.user.service.KakaoService;
import com.efub.agodaclone.user.service.RefreshTokenService;
import com.efub.agodaclone.user.service.UserService;
import com.efub.agodaclone.user.repository.RefreshTokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final KakaoService kakaoService;
    private final UserService userService;
    private final JwtProvider jwtProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final RefreshTokenService refreshTokenService;

    @Value("${frontend.url}")
    private String frontendUrl;

    //카카오 로그인
    @GetMapping("/oauth/login")
    public ResponseEntity<?> kakaoCallback(@RequestParam("code") String code, HttpServletResponse response) {
        String accessToken = kakaoService.getAccessToken(code); //인가 코드로 access token 발급
        KakaoUserResponseDto kakaoUserResponseDto = kakaoService.getUserInfo(accessToken); //사용자 정보 조회
        User user = userService.registerOrLogin(kakaoUserResponseDto); // 사용자 등록

        String jwt = jwtProvider.generateToken(user.getUserId()); //jwt token 생성
        String refreshToken = jwtProvider.generateRefreshToken(user.getUserId()); //jwt refresh token 생성

        refreshTokenService.saveOrUpdate(user.getUserId(), refreshToken, LocalDateTime.now().plusDays(14));

        // 카카오 token을 쿠키에 저장
        ResponseCookie kakaoCookie = ResponseCookie.from("kakao_token", accessToken)
                .path("/")
                .httpOnly(true)
                .secure(true) // https 환경 true
                .maxAge(60 * 60) // 1시간
                .sameSite("Lax")
                .build();

        // 쿠키로 access_token 설정
        ResponseCookie accessCookie = ResponseCookie.from("access_token", jwt)
                .path("/")
                .httpOnly(true) // JS에서 접근 불가능하게
                .secure(true)   // https 환경 true
                .maxAge(7 * 24 * 60 * 60)
                .sameSite("Lax")
                .build();

        // refresh_token 쿠키 설정
        ResponseCookie refreshCookie = ResponseCookie.from("refresh_token", refreshToken)
                .path("/oauth/reissue")
                .httpOnly(true)
                .secure(true)
                .maxAge(14 * 24 * 60 * 60) // 예: 14일
                .sameSite("Lax")
                .build();

        response.setHeader(HttpHeaders.SET_COOKIE, accessCookie.toString());
        response.addHeader(HttpHeaders.SET_COOKIE, refreshCookie.toString());
        response.addHeader(HttpHeaders.SET_COOKIE, kakaoCookie.toString());

        return ResponseEntity.ok().build();
    }

    // Access Token 재발급
    @PostMapping("/oauth/reissue")
    public ResponseEntity<Void> reissue(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = TokenUtil.extractRefreshTokenFromCookie(request); //쿠키에서 refresh token 추출

        // DB에서 refresh token 조회 및 검증
        Optional<RefreshToken> savedToken = refreshTokenRepository.findByToken(refreshToken);
        if (savedToken.isEmpty() || savedToken.get().getExpiration().isBefore(LocalDateTime.now())) {
            throw new AgodaException(ExceptionCode.REFRESH_TOKEN_INVALID);
        }

        // DB에 있으면 사용자 ID 추출하고 access token 재발급
        Long userId = savedToken.get().getUserId();
        String newAccessToken = jwtProvider.generateToken(userId);

        if (refreshToken == null) {
            throw new AgodaException(ExceptionCode.REFRESH_TOKEN_EMPTY);
        }

        // 새 access token 쿠키로 설정
        ResponseCookie accessCookie = ResponseCookie.from("access_token", newAccessToken)
                .path("/")
                .httpOnly(true)
                .secure(true)
                .maxAge(60 * 60) // 1시간
                .sameSite("Lax")
                .build();

        response.setHeader(HttpHeaders.SET_COOKIE, accessCookie.toString());
        return ResponseEntity.ok().build();

    }

    // 로그아웃
    @DeleteMapping("/oauth/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request, HttpServletResponse response) {

        Long userId = (Long) request.getAttribute("userId");

        // 카카오 앱 연결 해제
        String kakaoAccessToken = TokenUtil.extractKakaoTokenFromCookie(request);
        kakaoService.unlinkKakao(kakaoAccessToken);

        //DB에서 유저 삭제
        userService.deleteUser(userId);

        // access_token 쿠키 제거
        ResponseCookie accessCookie = ResponseCookie.from("access_token", "")
                .path("/")
                .httpOnly(true)
                .secure(true)
                .maxAge(0)
                .sameSite("Lax")
                .build();

        // refresh_token 쿠키 제거
        ResponseCookie refreshCookie = ResponseCookie.from("refresh_token", "")
                .path("/auth/reissue")
                .httpOnly(true)
                .secure(true)
                .maxAge(0)
                .sameSite("Lax")
                .build();

        // kakao token 쿠키 제거
        ResponseCookie kakaoCookie = ResponseCookie.from("kakao_token", "")
                .path("/")
                .httpOnly(true)
                .secure(true)
                .maxAge(0)
                .sameSite("Lax")
                .build();

        response.setHeader(HttpHeaders.SET_COOKIE, accessCookie.toString());
        response.addHeader(HttpHeaders.SET_COOKIE, refreshCookie.toString());
        response.addHeader(HttpHeaders.SET_COOKIE, kakaoCookie.toString());

        return ResponseEntity.noContent().build(); // 204 No Content 반환
    }

}
