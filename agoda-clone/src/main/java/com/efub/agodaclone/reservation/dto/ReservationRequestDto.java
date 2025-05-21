package com.efub.agodaclone.reservation.dto;

import com.efub.agodaclone.accomodation.domain.Accommodation;
import com.efub.agodaclone.reservation.domain.Reservation;
import com.efub.agodaclone.user.domain.User;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReservationRequestDto {

    @NotNull
    private Long accommodationId;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private LocalDate startDate;
    @NotNull
    private LocalDate endDate;
    @NotNull
    private String email;
    @NotNull
    private String phone;

    private String requestedTerm;

    public Reservation toEntity(User user, Accommodation accommodation){
        return Reservation.builder()
                .user(user)
                .accommodation(accommodation)
                .startDate(startDate)
                .endDate(endDate)
                .firstName(firstName)
                .lastName(lastName)
                .phone(phone)
                .requestedTerm(requestedTerm)
                .build();
    }
}
