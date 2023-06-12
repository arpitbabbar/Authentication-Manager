package com.babbarop.authenticationmanager.request;

import lombok.Data;

@Data
public class SignUpUserRequest {

    private String emailId;
    private String password;
    private String confirmPassword;
    private String firstName;
    private String lastName;
    private String mobileNumber;
    private String address;
    private String city;
    private String state;
    private String country;
    private String pincode;


}
