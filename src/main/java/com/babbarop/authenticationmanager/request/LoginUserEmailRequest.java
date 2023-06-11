package com.babbarop.authenticationmanager.request;

import lombok.Data;

@Data
public class LoginUserEmailRequest {

    private String email;
    private String password;

}
