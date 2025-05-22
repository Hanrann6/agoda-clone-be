package com.efub.agodaclone.review.controller;

import com.efub.agodaclone.global.exception.AgodaException;
import com.efub.agodaclone.global.exception.ExceptionCode;
import com.efub.agodaclone.review.dto.request.ReviewCreateRequest;
import com.efub.agodaclone.review.dto.request.ReviewUpdateRequest;
import com.efub.agodaclone.review.service.ReviewService;
import com.efub.agodaclone.user.domain.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    // 예약 리뷰 남기기
    @PostMapping
    public ResponseEntity<Void> addReview(@Valid @RequestBody ReviewCreateRequest request){
        Long reviewId = reviewService.addReview(request);
        return ResponseEntity.created(URI.create("/reviews/" + reviewId)).build();
    }

    // 예약 리뷰 수정
    @PatchMapping("/{reviewId}")
    public ResponseEntity<Void> updateReview(@Valid @RequestBody ReviewUpdateRequest request,
                                             @PathVariable("reviewId") Long reviewId){
        reviewService.updateReview(request, reviewId);
        return ResponseEntity.noContent().build();
    }
}
