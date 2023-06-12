package com.babbarop.authenticationmanager.response;

import lombok.Getter;

/**
 * @author Arpit Babbar
 */
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
    OTP_EXPIRED(1010, "FAIL", "OTP Expired. Please request for a new OTP"),
    EMAIL_ALREADY_EXISTS(1011, "FAIL", "Email ID already exists. Please try with other Email ID"),
    PASSWORD_LENGTH(1012, "FAIL", "Password length should be greater than or equals to 8 characters"),
    USER_NOT_FOUND(1013, "FAIL", "User not found"),
    USER_PROFILE_DELETED(1014, "SUCCESS", "User profile deleted successfully"),
    USER_PROFILE_UPDATED(1015, "SUCCESS", "User profile updated successfully"),
    USER_STATUS_UPDATED(1016, "SUCCESS", "User status updated successfully");


    private final int statusCode;
    private final String status;
    private final String errorMessage;

    AMErrorCodes(int statusCode, String status, String errorMessage) {
        this.statusCode = statusCode;
        this.status = status;
        this.errorMessage = errorMessage;
    }
}
