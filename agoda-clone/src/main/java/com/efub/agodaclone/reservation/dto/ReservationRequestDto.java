package com.efub.agodaclone.reservation.dto;

import com.efub.agodaclone.accomodation.domain.Accommodation;
import com.efub.agodaclone.reservation.domain.Reservation;
import com.efub.agodaclone.room.domain.Room;
import com.efub.agodaclone.user.domain.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReservationRequestDto {
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotNull
    private LocalDate startDate;
    @NotNull
    private LocalDate endDate;
    @NotBlank
    private String email;
    @NotBlank
    private String phone;

    private String requestedTerm;

    public Reservation toEntity(User user, Accommodation accommodation, Room room){
        return Reservation.builder()
                .user(user)
                .accommodation(accommodation)
                .room(room)
                .startDate(startDate)
                .endDate(endDate)
                .firstName(firstName)
                .lastName(lastName)
                .phone(phone)
                .requestedTerm(requestedTerm)
                .build();
    }
}
