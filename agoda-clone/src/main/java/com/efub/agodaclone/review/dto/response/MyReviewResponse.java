package com.efub.agodaclone.review.dto.response;

import com.efub.agodaclone.review.dto.summary.MyReviewSummary;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class MyReviewResponse {
    private List<MyReviewSummary> reservations;
}
