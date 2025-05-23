package com.efub.agodaclone.user.jwt;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

public class TokenUtil {

    public static String extractKakaoTokenFromCookie(HttpServletRequest request) {
        if (request.getCookies() == null) return null;

        for (Cookie cookie : request.getCookies()) {
            if ("kakao_token".equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }
}