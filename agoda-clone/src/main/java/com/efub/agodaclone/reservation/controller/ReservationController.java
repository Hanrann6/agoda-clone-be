package com.efub.agodaclone.reservation.controller;

import com.efub.agodaclone.reservation.dto.ReservationConfirmationResponseDto;
import com.efub.agodaclone.reservation.dto.ReservationListResponseDto;
import com.efub.agodaclone.reservation.dto.ReservationRequestDto;
import com.efub.agodaclone.reservation.service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    // 예약하기
    @PostMapping("/{accommodationId}/{roomId}")
    public ResponseEntity<ReservationConfirmationResponseDto> makeReservation(@PathVariable("accommodationId") Long accommodationId,
                                                                              @PathVariable("roomId") Long roomId,
                                                                              @RequestBody @Valid ReservationRequestDto requestDto){
        ReservationConfirmationResponseDto responseDto = reservationService.addReservation(accommodationId, roomId, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    // 다가오는 예약 조회
    @GetMapping("/upcoming")
    public ResponseEntity<ReservationListResponseDto> searchUpcomingReservations(){
        ReservationListResponseDto responseDto = reservationService.searchUpcomingReservations();
        return ResponseEntity.ok(responseDto);
    }
}
