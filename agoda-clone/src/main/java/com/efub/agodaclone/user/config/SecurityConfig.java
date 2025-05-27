package com.efub.agodaclone.user.config;

import com.efub.agodaclone.user.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
// Spring Security 설정 담당
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    // 허용 경로
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .formLogin(AbstractHttpConfigurer::disable) //자체 로그인 끔
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/**",
                                // "/index.html",
                                // "/kakao-login.html",
                                // "/oauth/reissue",
                                // "/oauth/login",
                                // "/css/**", "/js/**", "/images/**",
                                // "/favicon.ico", "/static/**", "/assets/**",

                                // //인증 없이 접근 가능한 api
                                // "/accommodations",
                                // "/accommodations/*",
                                // "/accommodations/*/rooms",
                                // "/rooms/*",
                                // "/accommodations/*/reviews",
                                // "/reviews/*"
                        ).permitAll()
                        .anyRequest().authenticated()

                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


}
