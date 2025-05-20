package com.efub.agodaclone.review.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "review_image")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewImage {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_img_id")
    private Long reviewImgId;

    @Column(name = "review_image", nullable = false)
    private String reviewImage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="review_id", nullable = false)
    private Review review;

    @Builder
    public ReviewImage(Review review, String reviewImage) {
        this.review = review;
        this.reviewImage = reviewImage;
    }
}
