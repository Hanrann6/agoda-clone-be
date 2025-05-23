package com.efub.agodaclone.reservation.service;

import com.efub.agodaclone.global.exception.AgodaException;
import com.efub.agodaclone.global.exception.ExceptionCode;
import com.efub.agodaclone.reservation.domain.Reservation;
import com.efub.agodaclone.reservation.repository.ReservationRepository;
import com.efub.agodaclone.review.domain.Review;
import com.efub.agodaclone.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReservationShareService {

    private final ReservationRepository reservationRepository;

    public Reservation findReservationById(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(()->new AgodaException(ExceptionCode.RESERVATION_NOT_FOUND));
    }

    public Reservation findReservationByReview(Review review) {
        return reservationRepository.findByReview(review)
                .orElseThrow(()->new AgodaException(ExceptionCode.RESERVATION_NOT_FOUND));
    }

    public User findUserByReservation(Reservation reservation) {
        return reservationRepository.findUserByReservation(reservation)
                .orElseThrow(()-> new AgodaException(ExceptionCode.RESERVATION_NOT_FOUND));
    }
}
