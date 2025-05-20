package com.efub.agodaclone.global.exception;

import org.springframework.http.HttpStatusCode;

public class AgodaException extends RuntimeException {
    private final ExceptionCode exceptionCode;

    public AgodaException(ExceptionCode exceptionCode){
        this.exceptionCode = exceptionCode;
    }

    @Override
    public String getMessage() {
        return exceptionCode.getMessage();
    }

    public HttpStatusCode getHttpStatusCode(){
        return exceptionCode.getHttpStatus();
    }

    public String getExceptionCodeName(){
        return exceptionCode.getClientExceptionCode().name();
    }

}
