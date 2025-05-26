package com.efub.agodaclone.review.dto.response;

import com.efub.agodaclone.accomodation.domain.Accommodation;
import com.efub.agodaclone.reservation.domain.Reservation;
import com.efub.agodaclone.review.domain.Review;
import com.efub.agodaclone.review.domain.ReviewImage;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
public class ReviewDetailResponse {

    private AccommodationInfo accommodation;
    private ReservationInfo reservation;
    private ReviewInfo review;

    @Getter
    @Builder
    public static class AccommodationInfo {
        private String engName;
        private String location;
        private double averageScore;
        private int reviewCount;

        public static AccommodationInfo from(Accommodation accommodation, int reviewCount) {
            return AccommodationInfo.builder()
                    .engName(accommodation.getEngName())
                    .location(accommodation.getLocation())
                    .averageScore(accommodation.getTotalScore() != null ? accommodation.getTotalScore() : 0.0 / reviewCount)
                    .reviewCount(reviewCount)
                    .build();
        }
    }

    @Getter
    @Builder
    public static class ReservationInfo {
        private LocalDate startDate;
        private LocalDate endDate;

        public static ReservationInfo from(Reservation reservation){
            return ReservationInfo.builder()
                    .startDate(reservation.getStartDate())
                    .endDate(reservation.getEndDate())
                    .build();
        }
    }

    @Getter
    @Builder
    public static class ReviewInfo {
        private Long reviewId;
        private int locationScore;
        private int cleanScore;
        private int serviceScore;
        private String reviewText;
        private List<String> reviewImages;
        private LocalDateTime createdAt;

        public static ReviewInfo from(Review review){
            return ReviewInfo.builder()
                    .reviewId(review.getReviewId())
                    .locationScore(review.getLocationScore())
                    .cleanScore(review.getCleanlinessScore())
                    .serviceScore(review.getServiceScore())
                    .reviewText(review.getContent())
                    .reviewImages(
                            review.getReviewImages()
                                    .stream().map(ReviewImage::getReviewImage)
                                    .toList())
                    .createdAt(review.getCreatedAt())
                    .build();
        }
    }
}
