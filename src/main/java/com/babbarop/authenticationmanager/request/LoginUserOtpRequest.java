package com.babbarop.authenticationmanager.request;

import lombok.Data;

@Data
public class LoginUserOtpRequest {

    private String email;
    private String otp;
}
