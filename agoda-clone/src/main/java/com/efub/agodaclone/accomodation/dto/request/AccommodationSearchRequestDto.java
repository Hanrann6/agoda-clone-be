package com.efub.agodaclone.accomodation.dto.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccommodationSearchRequestDto {

    @NotBlank(message = "검색어를 입력해주세요.")
    private String query;

    @FutureOrPresent(message = "시작일은 오늘 이후여야 합니다.")
    private LocalDate startDate;

    @Future(message = "숙소 종료일은 시작일 다음날 이후로 선택해 주세요.")
    private LocalDate endDate;
}