package com.efub.agodaclone.reservation.repository;

import com.efub.agodaclone.reservation.domain.Reservation;
import com.efub.agodaclone.review.domain.Review;
import com.efub.agodaclone.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    // 사용자의 예약 정보 조회
    List<Reservation> findAllByUserOrderByStartDateAsc(User user);


    @Query("SELECT r.user FROM Reservation r WHERE r = :reservation")
    Optional<User> findUserByReservation(@Param("reservation") Reservation reservation);

    Optional<Reservation> findByReview(Review review);
}
