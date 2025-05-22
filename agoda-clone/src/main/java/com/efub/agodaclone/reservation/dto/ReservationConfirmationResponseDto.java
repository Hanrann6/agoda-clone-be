package com.efub.agodaclone.reservation.dto;

import com.efub.agodaclone.reservation.domain.Reservation;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter @Builder
public class ReservationConfirmationResponseDto {

    private Long reservationId;
    private LocalDateTime createdAt;
    private String message;

    public static ReservationConfirmationResponseDto of(Reservation reservation){
        return ReservationConfirmationResponseDto.builder()
                .reservationId(reservation.getReservationId())
                .createdAt(reservation.getCreatedAt())
                .message("예약이 완료되었습니다.")
                .build();
    }
}
