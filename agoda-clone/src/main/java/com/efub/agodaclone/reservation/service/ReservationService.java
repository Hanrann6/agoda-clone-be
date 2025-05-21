package com.efub.agodaclone.reservation.service;

import com.efub.agodaclone.accomodation.domain.Accommodation;
import com.efub.agodaclone.accomodation.service.AccommodationService;
import com.efub.agodaclone.reservation.domain.Reservation;
import com.efub.agodaclone.reservation.dto.ReservationConfirmationResponseDto;
import com.efub.agodaclone.reservation.dto.ReservationListResponseDto;
import com.efub.agodaclone.reservation.dto.ReservationRequestDto;
import com.efub.agodaclone.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ReservationService {

    private final AccommodationService accommodationService;
    private final ReservationRepository reservationRepository;
//    private final UserService userService;

//    // 예약하기
//    public ReservationConfirmationResponseDto addReservation(ReservationRequestDto requestDto) {
//        User findUser = UserService.findCurrentMember(); // 현재 로그인된 유저 찾아오기
//        Accommodation findAccommodation = accommodationService.findAccommodationById(requestDto.getAccommodationId());
//        Reservation reservation = requestDto.toEntity(findUser, findAccommodation);
//        reservationRepository.save(reservation);
//
//        return ReservationConfirmationResponseDto.of(reservation);
//    }

//    // 나의 예약 전체 조회
//    public ReservationListResponseDto searchUpcomingReservations() {
//        User findUser = UserService.findCurrentMember(); // 현재 로그인된 유저 찾아오기
//        List<Reservation> reservations = reservationRepository.findAllByUserOrderByStartDateAsc(findUser);
//        // 오늘 날짜 가져오기
//        LocalDate today = LocalDate.now();
//
//        Reservation upcoming = reservations.stream()
//                .filter(r -> !r.getStartDate().isBefore(today))  // startDate >= today 들 중 임박한 1개
//                .min(Comparator.comparing(Reservation::getStartDate))
//                .orElse(null);
//
//        List<Reservation> completed = reservations.stream()
//                .filter(r -> r.getStartDate().isBefore(today)) // startDate < today
//                .collect(Collectors.toList());
//
//        return ReservationListResponseDto.of(upcoming, completed, today, this::calculateStatus);
//    }

    // 예약 상태 가져오는 함수
    private String calculateStatus(Reservation reservation, LocalDate today) {
        if (reservation.getEndDate().isBefore(today)) {
            return "체크아웃 완료";
        } else return "체크인 완료";
    }
}
