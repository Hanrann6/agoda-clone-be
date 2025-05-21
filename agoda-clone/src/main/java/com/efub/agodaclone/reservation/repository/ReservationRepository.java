package com.efub.agodaclone.reservation.repository;

import com.efub.agodaclone.reservation.domain.Reservation;
import com.efub.agodaclone.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    // 사용자의 예약 정보 조회
    List<Reservation> findAllByUserOrderByStartDateAsc(User user);


}
