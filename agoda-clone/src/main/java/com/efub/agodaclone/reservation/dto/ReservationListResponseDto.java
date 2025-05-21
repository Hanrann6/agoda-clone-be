package com.efub.agodaclone.reservation.dto;

import com.efub.agodaclone.accomodation.domain.AccommodationImage;
import com.efub.agodaclone.reservation.domain.Reservation;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

@Getter @Builder
public class ReservationListResponseDto {

    private SingleReservation upcoming;
    private List<SingleReservation> completed;

    @Getter @Builder
    public static class SingleReservation {
        private Long reservationId;
        private String korName;
        private String accommodationImage;
        private String location;
        private LocalDate startDate;
        private LocalDate endDate;
        private String status;

        public static SingleReservation from(Reservation reservation, String status){
            AccommodationImage accommodationImage = reservation.getAccommodation().getAccommodationImageList().get(0);
            return SingleReservation.builder()
                    .reservationId(reservation.getReservationId())
                    .korName(reservation.getAccommodation().getKorName())
                    .accommodationImage(accommodationImage.getImgUrl())
                    .location(reservation.getAccommodation().getLocation())
                    .startDate(reservation.getStartDate())
                    .endDate(reservation.getEndDate())
                    .status(status)
                    .build();
        }
    }

    public static ReservationListResponseDto of(Reservation upcoming, List<Reservation> completed, LocalDate today, BiFunction<Reservation, LocalDate, String> statusCalculator) {
        return ReservationListResponseDto.builder()
                .upcoming(SingleReservation.from(upcoming, "예약 완료"))
                .completed(completed.stream()
                        .map(r -> SingleReservation.from(r, statusCalculator.apply(r, today)))
                        .collect(Collectors.toList())
                )
                .build();
    }
}
