package com.efub.agodaclone.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionCode {
    // 전체
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, ClientExceptionCode.INTERNAL_SERVER_ERROR,
            "예상치 못한 서버에러가 발생했습니다"),
    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, ClientExceptionCode.ILLEGAL_ARGUMENT,
            "잘못된 인자입니다."),
    INVALID_PAGE(HttpStatus.BAD_REQUEST, ClientExceptionCode.INVALID_PAGE,
            "요청한 페이지 번호는 0 이상의 정수여야 합니다."),
    INVALID_DATE_RANGE(HttpStatus.BAD_REQUEST, ClientExceptionCode.INVALID_DATE_RANGE,
            "체크아웃 날짜는 체크인 날짜 이후로 설정해주세요."),

    //Auth
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, ClientExceptionCode.UNAUTH_ERROR,
            "권한이 없는 사용자입니다. 접근이 제한되었습니다."),
    OAUTH_TOKEN_INTERNAL_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, ClientExceptionCode.OAUTH_SERVER_ERROR,
            "카카오 서버와 통신하는 과정 중 예상치 못한 예외가 발생했습니다."),
    ACCESS_TOKEN_EMPTY(HttpStatus.UNAUTHORIZED, ClientExceptionCode.ACCESS_TOKEN_EMPTY,
            "액세스 토큰이 존재하지 않습니다. 액세스 토큰을 발급해주세요."),
    REFRESH_TOKEN_EMPTY(HttpStatus.UNAUTHORIZED, ClientExceptionCode.REFRESH_TOKEN_EMPTY,
            "리프레시 토큰이 존재하지 않습니다. 다시 로그인해주세요."),
    REFRESH_TOKEN_INVALID(HttpStatus.UNAUTHORIZED, ClientExceptionCode.REFRESH_TOKEN_INVALID,
            "리프레시 토큰이 존재하지 않습니다. 다시 로그인해주세요."),

    // 예약
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, ClientExceptionCode.RESOURCE_NOT_FOUND,
            "해당 숙소 또는 객실이 존재하지 않습니다."),
    DUPLICATE_RESERVATION(HttpStatus.CONFLICT, ClientExceptionCode.DUPLICATE_RESERVATION,
            "해당 시간대는 이미 예약이 완료되었습니다."),
    RESERVATION_NOT_FOUND(HttpStatus.NOT_FOUND, ClientExceptionCode.RESERVATION_NOT_FOUND,
            "해당 예약이 존재하지 않습니다."),

    // 리뷰
    NO_REVIEW_RATING(HttpStatus.BAD_REQUEST, ClientExceptionCode.ILLEGAL_ARGUMENT,
            "리뷰 별점을 입력하지 않았습니다."),
    NO_IMAGE_ERROR(HttpStatus.BAD_REQUEST, ClientExceptionCode.NO_IMAGE_ERROR,
            "이미지를 보내주세요");


    private final HttpStatus httpStatus;
    private final ClientExceptionCode clientExceptionCode;
    private final String message;

    ExceptionCode(HttpStatus httpStatus, ClientExceptionCode clientExceptionCode, String message) {
        this.httpStatus = httpStatus;
        this.clientExceptionCode = clientExceptionCode;
        this.message = message;
    }
}
