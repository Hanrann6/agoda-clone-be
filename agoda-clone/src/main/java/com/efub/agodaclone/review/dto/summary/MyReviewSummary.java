package com.efub.agodaclone.review.dto.summary;

import com.efub.agodaclone.accomodation.domain.Accommodation;
import com.efub.agodaclone.reservation.domain.Reservation;
import com.efub.agodaclone.review.domain.Review;
import com.efub.agodaclone.review.dto.response.MyReviewResponse;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class MyReviewSummary {

    private Long reservationId;
    private String engName;
    private String accommodationImage;
    private String location;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean hasReviewed;
    private Long reviewId;

    public static MyReviewSummary from( Reservation reservation) {
        return MyReviewSummary.builder()
                .reservationId(reservation.getReservationId())
                .engName(reservation.getAccommodation().getEngName())
                .location(reservation.getAccommodation().getLocation())
                .startDate(reservation.getStartDate())
                .endDate(reservation.getEndDate())
                .hasReviewed(reservation.getReview() != null)
                .reviewId(reservation.getReview().getReviewId())
                .build();
    }
}
