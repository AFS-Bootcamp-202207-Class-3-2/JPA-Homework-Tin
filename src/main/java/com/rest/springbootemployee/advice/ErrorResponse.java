package com.rest.springbootemployee.advice;


public class ErrorResponse {

    private int errorCode;

    private String errorMessage;

    public ErrorResponse(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
