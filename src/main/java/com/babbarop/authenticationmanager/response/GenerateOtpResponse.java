package com.babbarop.authenticationmanager.response;

import lombok.Data;

@Data
public class GenerateOtpResponse extends AMGenericResponse{

    private String otp;

}
