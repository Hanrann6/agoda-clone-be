package com.efub.agodaclone.review.service;

import com.efub.agodaclone.global.exception.AgodaException;
import com.efub.agodaclone.global.exception.ExceptionCode;
import com.efub.agodaclone.reservation.domain.Reservation;
import com.efub.agodaclone.reservation.service.ReservationService;
import com.efub.agodaclone.review.domain.Review;
import com.efub.agodaclone.review.dto.request.ReviewCreateRequest;
import com.efub.agodaclone.review.dto.request.ReviewUpdateRequest;
import com.efub.agodaclone.review.repository.ReviewRepository;
import com.efub.agodaclone.user.domain.User;
import com.efub.agodaclone.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReservationService reservationService;
    private final UserService userService;


    public Long addReview(ReviewCreateRequest reviewCreateRequest){
        User user = userService.getCurrentUser();
        Long reservationId = reviewCreateRequest.getReservationId();
        Reservation reservation = reservationService.findReservationById(reservationId);
        validateReservationOwnership(user, reservation);
        Review newReview = Review.create(reservation, reviewCreateRequest);
        reviewRepository.save(newReview);
        return newReview.getReviewId();
    }

    public void updateReview(ReviewUpdateRequest reviewUpdateRequest, Long reviewId){
        User user = userService.getCurrentUser();
        Review review = getReviewById(reviewId);
        Reservation reservation = review.getReservation();
        validateReservationOwnership(user, reservation);
        review.updateReview(reviewUpdateRequest);
    }

    public void deleteReview(Long reviewId){
        User user = userService.getCurrentUser();
        Review review = getReviewById(reviewId);
        validateReservationOwnership(user, review.getReservation());
        reviewRepository.delete(review);
    }

    private void validateReservationOwnership(User user, Reservation reservation){
        Long writerId = user.getUserId();
        Long reserovatorId = reservation.getReservationId();
        if(!writerId.equals(reserovatorId)){
            throw new AgodaException(ExceptionCode.UNAUTHORIZED);
        }
    }

    public Review getReviewById(Long reviewId){
        return reviewRepository.findByReviewId(reviewId)
                .orElseThrow(()->new AgodaException(ExceptionCode.RESOURCE_NOT_FOUND));
    }
}
