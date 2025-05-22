package com.efub.agodaclone.user.controller;

import com.efub.agodaclone.user.dto.KakaoUserInfo;
import com.efub.agodaclone.user.domain.User;
import com.efub.agodaclone.user.dto.AuthResponse;
import com.efub.agodaclone.user.jwt.JwtProvider;
import com.efub.agodaclone.user.service.KakaoService;
import com.efub.agodaclone.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final KakaoService kakaoService;
    private final UserService userService;
    private final JwtProvider jwtProvider;

    @GetMapping("/kakao/callback")
    public ResponseEntity<?> kakaoCallback(@RequestParam("code") String code) {
        String accessToken = kakaoService.getAccessToken(code);
        KakaoUserInfo kakaoUserInfo = kakaoService.getUserInfo(accessToken);
        User user = userService.registerOrLogin(kakaoUserInfo);
        String jwt = jwtProvider.generateToken(user.getUserId());

        return ResponseEntity.ok(new AuthResponse(jwt));
    }


    @DeleteMapping("/oauth/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request, HttpServletResponse response) {

        Long userId = (Long) request.getAttribute("userId");
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

        response.setHeader(HttpHeaders.SET_COOKIE, accessCookie.toString());
        response.addHeader(HttpHeaders.SET_COOKIE, refreshCookie.toString());

        return ResponseEntity.noContent().build(); // 204 No Content
    }
}
