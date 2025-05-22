package com.efub.agodaclone.review.dto.request;

import com.efub.agodaclone.global.validation.ValidScore;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ReviewUpdateRequest {
    @ValidScore
    int locationScore;

    @ValidScore
    int cleanScore;

    @ValidScore
    int serviceScore;

    String reviewText;

    List<String> reviewImages = new ArrayList<>();
}
