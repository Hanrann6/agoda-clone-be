package com.efub.agodaclone.review.service;

import com.efub.agodaclone.accomodation.domain.Accommodation;
import com.efub.agodaclone.accomodation.service.AccommodationService;
import com.efub.agodaclone.global.exception.AgodaException;
import com.efub.agodaclone.global.exception.ExceptionCode;
import com.efub.agodaclone.reservation.domain.Reservation;
import com.efub.agodaclone.reservation.service.ReservationService;
import com.efub.agodaclone.reservation.service.ReservationShareService;
import com.efub.agodaclone.review.domain.Review;
import com.efub.agodaclone.review.dto.request.ReviewCreateRequest;
import com.efub.agodaclone.review.dto.request.ReviewUpdateRequest;
import com.efub.agodaclone.review.dto.response.ReviewDetailResponse;
import com.efub.agodaclone.review.repository.ReviewRepository;
import com.efub.agodaclone.user.domain.User;
import com.efub.agodaclone.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReservationShareService reservationService;
    private final UserService userService;
    private final AccommodationService accommodationService;

    @Transactional
    public Long addReview(ReviewCreateRequest reviewCreateRequest){
        User user = userService.getCurrentUser();
        Long reservationId = reviewCreateRequest.getReservationId();
        Reservation reservation = reservationService.findReservationById(reservationId);
        validateReservationOwnership(user, reservation);
        Review newReview = Review.create(reservation, reviewCreateRequest);
        reviewRepository.save(newReview);
        return newReview.getReviewId();
    }

    @Transactional
    public void updateReview(ReviewUpdateRequest reviewUpdateRequest, Long reviewId){
        User user = userService.getCurrentUser();
        Review review = getReviewById(reviewId);
        Reservation reservation = review.getReservation();
        validateReservationOwnership(user, reservation);
        review.updateReview(reviewUpdateRequest);
    }
    @Transactional
    public void deleteReview(Long reviewId){
        User user = userService.getCurrentUser();
        Review review = getReviewById(reviewId);
        Reservation reservation = reservationService.findReservationByReview(review);
        reservation.removeReview();
        validateReservationOwnership(user, reservation);
        reviewRepository.deleteById(review.getReviewId());
    }

    @Transactional(readOnly = true)
    public ReviewDetailResponse getReviewDetail(Long reviewId){
        User user = userService.getCurrentUser();
        Review review = getReviewById(reviewId);
        Reservation reservation = reservationService.findReservationByReview(review);
        Accommodation accommodation = reservation.getAccommodation();
        int reviewCount = accommodationService.getReviewCount(accommodation.getAccommodationId());
        return ReviewDetailResponse.builder()
                .accommodation(ReviewDetailResponse.AccommodationInfo.from(accommodation, reviewCount))
                .reservation(ReviewDetailResponse.ReservationInfo.from(reservation))
                .review(ReviewDetailResponse.ReviewInfo.from(review))
                .build();

    }

    public void validateReservationOwnership(User user, Reservation reservation){
        Long writerId = user.getUserId();
        Long reserovatorId = reservationService.findUserByReservation(reservation).getUserId();
        log.info("{} : {}", writerId, reserovatorId);
        if(!writerId.equals(reserovatorId)){
            throw new AgodaException(ExceptionCode.UNAUTHORIZED);
        }
    }

    public Review getReviewById(Long reviewId){
        return reviewRepository.findByReviewId(reviewId)
                .orElseThrow(()->new AgodaException(ExceptionCode.RESOURCE_NOT_FOUND));
    }
}
