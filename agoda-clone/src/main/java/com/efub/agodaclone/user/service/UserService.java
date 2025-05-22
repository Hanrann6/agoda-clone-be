package com.efub.agodaclone.user.service;

import com.efub.agodaclone.user.domain.CustomUserDetails;
import com.efub.agodaclone.user.dto.KakaoUserInfo;
import com.efub.agodaclone.user.domain.User;
import com.efub.agodaclone.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.efub.agodaclone.global.exception.ClientExceptionCode;


import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

//    @Transactional(readOnly = true)
//    public Optional<User> findByKakaoId(String kakaoId) {
//        return userRepository.findByKakaoId(kakaoId);
//    }
//
//    @Transactional
//    public User registerKakaoUser(String kakaoId, String email, String name) {
//        User user = User.builder()
//                .kakaoId(kakaoId)
//                .email(email)
//                .name(name)
//                .createdAt(LocalDateTime.now())
//                .build();
//        return userRepository.save(user);
//    }

//    @Transactional
//    public User loginOrRegister(String kakaoId, String email, String name) {
//        return userRepository.findByKakaoId(kakaoId)
//                .orElseGet(() -> registerKakaoUser(kakaoId, email, name));
//    }

    public User registerOrLogin(KakaoUserInfo kakaoUserInfo) {
        String kakaoId = String.valueOf(kakaoUserInfo.getId());
        Optional<User> userOptional = userRepository.findByKakaoId(kakaoId);

        if (userOptional.isPresent()) {
            return userOptional.get();
        } else {
            User newUser = User.builder()
                    .kakaoId(kakaoId)
                    .email(kakaoUserInfo.getKakaoAccount().getEmail())
                    .name(kakaoUserInfo.getKakaoAccount().getProfile().getNickname())
                    .createdAt(LocalDateTime.now())
                    .build();
            return userRepository.save(newUser);
        }
    }

    @Transactional(readOnly = true)
    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated() || auth.getPrincipal().equals("anonymousUser")) {
            throw new RuntimeException(ClientExceptionCode.UNAUTHORIZED.name());
        }

        Object principal = auth.getPrincipal();
        if (!(principal instanceof CustomUserDetails userDetails)) {
            throw new RuntimeException(ClientExceptionCode.UNAUTH_ERROR.name());
        }

        return userRepository.findById(userDetails.getId())
                .orElseThrow(() -> new RuntimeException(ClientExceptionCode.RESOURCE_NOT_FOUND.name()));
    }

}
