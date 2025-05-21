package com.efub.agodaclone.review.dto.request;

import com.efub.agodaclone.global.validation.ValidScore;
import com.efub.agodaclone.reservation.domain.Reservation;
import com.efub.agodaclone.review.domain.Review;
import com.efub.agodaclone.review.domain.ReviewImage;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Getter
@NoArgsConstructor
public class ReviewCreateRequest {

    @NotNull(message = "reservation id가 null이어서는 안됩니다.")
    Long reservationId;

    @ValidScore
    int locationScore;

    @ValidScore
    int cleanScore;

    @ValidScore
    int serviceScore;

    @Length(max = 1000)
    String reviewText;

    List<String> reviewImages;

    public Review toEntity(Reservation reservation, List<ReviewImage> reviewImages) {
        return Review.builder()
                .locationScore(locationScore)
                .cleanlinessScore(cleanScore)
                .serviceScore(serviceScore)
                .content(reviewText)
                .reservation(reservation)
                .reviewImages(reviewImages)
                .build();
    }
}
