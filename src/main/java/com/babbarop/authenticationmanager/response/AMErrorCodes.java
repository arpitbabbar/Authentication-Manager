package com.babbarop.authenticationmanager.response;

import lombok.Getter;

@Getter
public enum AMErrorCodes {

    SUCCESS(1001, "SUCCESS", "Success"),
    FAIL(1000, "FAIL", "Something went wrong"),
    PASSWORD_MISMATCH(1003, "FAIL", "Password doesn't match"),
    INVALID_EMAIL(1004, "FAIL", "Invalid Email ID. Please enter a valid Email ID"),
    INTERNAL_ERROR(1005, "FAIL", "Authentication Manager Internal Error"),
    OTP_GENERATED(1006, "SUCCESS", "OTP Generated"),
    USER_BLOCKED(1007, "FAIL", "Your Request is Blocked. Due to multiple failed attempts. Try after some time"),
    WAIT_OTP(1008, "FAIL", "Please wait for 30 seconds before requesting for OTP again"),
    OTP_MISMATCH(1009, "FAIL", "Wrong OTP Entered"),
    OTP_EXPIRED(1010, "FAIL", "OTP Expired. Please request for a new OTP");

    private final int statusCode;
    private final String status;
    private final String errorMessage;

    AMErrorCodes(int statusCode, String status, String errorMessage) {
        this.statusCode = statusCode;
        this.status = status;
        this.errorMessage = errorMessage;
    }
}
