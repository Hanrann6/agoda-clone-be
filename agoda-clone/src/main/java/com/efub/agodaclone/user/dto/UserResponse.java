package com.efub.agodaclone.user.dto;

import com.efub.agodaclone.user.domain.User;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    private Long id;
    private String email;
    private String name;

    public static UserResponse from(User user) {
        return UserResponse.builder()
                .id(user.getUserId())
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }
}