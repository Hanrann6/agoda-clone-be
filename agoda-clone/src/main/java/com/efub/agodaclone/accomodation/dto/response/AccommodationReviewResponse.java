package com.efub.agodaclone.accomodation.dto.response;

import com.efub.agodaclone.accomodation.dto.summary.AccommodationReviewSummary;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class AccommodationReviewResponse {
    List<AccommodationReviewSummary> reviews;
    private double totalAverageScore;
    private Long totalReviewCount;

    public static AccommodationReviewResponse to(List<AccommodationReviewSummary> reviews, double totalAverageScore, Long totalReviewCount) {
        return AccommodationReviewResponse.builder()
                .reviews(reviews)
                .totalAverageScore(totalAverageScore)
                .totalReviewCount(totalReviewCount)
                .build();
    }
}
