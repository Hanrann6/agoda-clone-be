package com.efub.agodaclone.user.jwt;

import com.efub.agodaclone.global.exception.AgodaException;
import com.efub.agodaclone.global.exception.ExceptionCode;
import com.efub.agodaclone.user.domain.CustomUserDetails;
import com.efub.agodaclone.user.domain.User;
import com.efub.agodaclone.user.repository.UserRepository;
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
    //Spring Security에서 JWT 인증을 수행

    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;

    //모든 요청마다 수행
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String token = TokenUtil.extractTokenFromHeaderOrCookie(request); //토큰 추출

        // toke 유효성 검사 후 사용자 조회
        if (token != null) {
            Long userId = jwtProvider.validateAndGetUserId(token);

            User user = userRepository.findById(userId).orElseThrow(()-> new AgodaException(ExceptionCode.RESOURCE_NOT_FOUND));
            CustomUserDetails userDetails = new CustomUserDetails(user.getUserId(), user.getEmail(), user.getName());

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userDetails, null,
                            List.of(new SimpleGrantedAuthority("ROLE_USER")));

            request.setAttribute("userId", userId);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }


}