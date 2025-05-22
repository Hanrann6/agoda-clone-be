package com.efub.agodaclone.user.controller;

import com.efub.agodaclone.user.dto.KakaoUserInfo;
import com.efub.agodaclone.user.domain.User;
import com.efub.agodaclone.user.dto.AuthResponse;
import com.efub.agodaclone.user.jwt.JwtProvider;
import com.efub.agodaclone.user.service.KakaoService;
import com.efub.agodaclone.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
}
