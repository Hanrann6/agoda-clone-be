package com.efub.agodaclone.accomodation.dto.summary;

import com.efub.agodaclone.accomodation.dto.response.AccommodationReviewResponse;
import com.efub.agodaclone.review.domain.Review;
import com.efub.agodaclone.review.domain.ReviewImage;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
public class AccommodationReviewSummary {
    private Long reviewId;
    private String name;
    private double averageScore;
    private String reviewText;
    private List<String> reviewImages;
    private LocalDateTime createdAt;

    public static AccommodationReviewSummary to(Review review){
        double averageScore = (review.getCleanlinessScore() + review.getServiceScore() + review.getLocationScore())/3.0;
        return AccommodationReviewSummary.builder()
                .reviewId(review.getReviewId())
                .name(review.getReservation().getLastName() + review.getReservation().getFirstName())
                .averageScore(averageScore)
                .reviewText(review.getContent())
                .reviewImages(review.getReviewImages().stream().map(ReviewImage::getReviewImage).collect(Collectors.toList()))
                .createdAt(review.getCreatedAt())
                .build();
    }
}
