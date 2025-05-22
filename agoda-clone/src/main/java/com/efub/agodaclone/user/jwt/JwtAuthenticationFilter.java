package com.efub.agodaclone.user.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            try {
                Long userId = jwtProvider.validateAndGetUserId(token);

                // Spring Security가 인식할 수 있게 인증 객체 등록
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userId, null,
                                List.of(new SimpleGrantedAuthority("ROLE_USER")));


                // userId 저장
                request.setAttribute("userId", userId); // 간단하게 저장하는 예시
                SecurityContextHolder.getContext().setAuthentication(authentication);


                System.out.println("🔐 인증된 유저 ID: " + userId);
                System.out.println("🔐 권한: " + authentication.getAuthorities());


            } catch (RuntimeException e) {
                System.out.println("JWT 검증 실패: " + e.getMessage());
            }
        }

        filterChain.doFilter(request, response);
    }
}
