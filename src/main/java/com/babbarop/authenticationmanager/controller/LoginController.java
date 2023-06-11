package com.babbarop.authenticationmanager.controller;

import com.babbarop.authenticationmanager.request.LoginUserEmailRequest;
import com.babbarop.authenticationmanager.request.LoginUserOtpRequest;
import com.babbarop.authenticationmanager.service.LoginService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

/**
 * @author Arpit Babbar
 * @since 2023-06-12
 */

@RestController("/login")
public class LoginController {

    final
    LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    private static final Logger LOGGER = Logger.getLogger(LoginController.class.getName());

    // write a get method te output hello when we hit it
    @GetMapping("/hello")
    public String hello(){
        return "Hello";
    }

    @PostMapping("/user/email")
    public ResponseEntity<?> loginUser(@RequestBody LoginUserEmailRequest request){
        LOGGER.info("LoginController.loginUser() called");
        ResponseEntity<?> response = null;
        try {
            LOGGER.info("LoginController.loginUser() request: " + request);
           response = loginService.loginUser(request);
           LOGGER.info("LoginController.loginUser() response: " + response);
        }
        catch (Exception e) {
            LOGGER.info("LoginController.loginUser() exception: " + e);
        }
        return response;
    }

    @GetMapping("/generate/otp/{email_id}")
    public ResponseEntity<?> loginGenerateOtp(@PathVariable("email_id") String emailId){
        LOGGER.info("LoginController.loginGenerateOtp() called");
        ResponseEntity<?> response = null;
        try {
            LOGGER.info("LoginController.loginGenerateOtp() emailId: " + emailId);
            response =  loginService.generateOtp(emailId);
            LOGGER.info("LoginController.loginGenerateOtp() response: " + response);
        }
        catch (Exception e) {
            LOGGER.info("LoginController.loginGenerateOtp() exception: " + e);
        }
        return response;
    }

    @PostMapping("/user/otp")
    public ResponseEntity<?> loginUserOtp(@RequestBody LoginUserOtpRequest request){
        LOGGER.info("LoginController.loginUserOtp() called");
        ResponseEntity<?> response = null;
        try {
            LOGGER.info("LoginController.loginUserOtp() request: " + request);
            response =  loginService.loginUserOtp(request);
            LOGGER.info("LoginController.loginUserOtp() response: " + response);
        }
        catch (Exception e) {
        LOGGER.info("LoginController.loginUserOtp() exception: " + e);
        }
        return response;

    }
}


