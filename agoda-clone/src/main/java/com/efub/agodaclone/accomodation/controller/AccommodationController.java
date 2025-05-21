package com.efub.agodaclone.accomodation.controller;

import com.efub.agodaclone.accomodation.dto.request.AccommodationSearchRequestDto;
import com.efub.agodaclone.accomodation.dto.response.AccommodationDetailResponseDto;
import com.efub.agodaclone.accomodation.dto.response.AccommodationSearchListResponseDto;
import com.efub.agodaclone.accomodation.service.AccommodationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/accommodations")
@RequiredArgsConstructor
public class AccommodationController {

    private final AccommodationService accommodationService;

    // 숙소 리스트 조회
    @GetMapping
    public ResponseEntity<AccommodationSearchListResponseDto> searchAccommodation(@RequestParam String query,
                                                                                  @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                                                  @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                                                                                  @RequestParam int page){
        AccommodationSearchListResponseDto responseDto = accommodationService.getAccommodationList(query, startDate, endDate, page);
        return ResponseEntity.ok(responseDto);
    }

    // 숙소 상세 조회
    @GetMapping("/{accommodationId}")
    public ResponseEntity<AccommodationDetailResponseDto> getAccommodationDetail(@PathVariable("accommodationId") Long accommodationId){
        AccommodationDetailResponseDto responseDto = accommodationService.getDetailedAccommodation(accommodationId);
        return ResponseEntity.ok(responseDto);
    }

}
