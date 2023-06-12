package com.babbarop.authenticationmanager.controller;

import com.babbarop.authenticationmanager.request.SignUpUserRequest;
import com.babbarop.authenticationmanager.request.UpdateUserRequest;
import com.babbarop.authenticationmanager.service.UserService;
import com.babbarop.authenticationmanager.util.AmEnums;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger LOGGER = Logger.getLogger(UserController.class.getName());

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/signup")
    public ResponseEntity<?> signUpUser(@RequestBody SignUpUserRequest request){
        LOGGER.info("LoginController.signUpUser() called");
        ResponseEntity<?> response = null;
        try {
            LOGGER.info("LoginController.signUpUser() request: " + request);
            response =  userService.signUpUser(request);
            LOGGER.info("LoginController.signUpUser() response: " + response);
        }
        catch (Exception e) {
            LOGGER.info("LoginController.signUpUser() exception: " + e);
        }
        return response;
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody UpdateUserRequest request){
        LOGGER.info("LoginController.updateUser() called");
        ResponseEntity<?> response = null;
        try {
            LOGGER.info("LoginController.updateUser() request: " + request);
            response =  userService.updateUser(request);
            LOGGER.info("LoginController.updateUser() response: " + response);
        }
        catch (Exception e) {
            LOGGER.info("LoginController.updateUser() exception: " + e);
        }
        return response;
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId){
        LOGGER.info("LoginController.deleteUser() called");
        ResponseEntity<?> response = null;
        try {
            LOGGER.info("LoginController.deleteUser() userId: " + userId);
            response =  userService.deleteUser(userId);
            LOGGER.info("LoginController.deleteUser() response: " + response);
        }
        catch (Exception e) {
            LOGGER.info("LoginController.deleteUser() exception: " + e);
        }
        return response;
    }

    @GetMapping("/get/{userId}")
    public ResponseEntity<?> getUserDetails(Long userId){
        LOGGER.info("LoginController.getUserDetails() called");
        ResponseEntity<?> response = null;
        try {
            LOGGER.info("LoginController.getUserDetails() userId: " + userId);
            response =  userService.getUserDetails(userId);
            LOGGER.info("LoginController.getUserDetails() response: " + response);
        }
        catch (Exception e) {
            LOGGER.info("LoginController.getUserDetails() exception: " + e);
        }
        return response;
    }

    @GetMapping("changeStatus/{type}/{userId}/{status}")
    public ResponseEntity<?> changeUserStatus(@PathVariable AmEnums.StatusRequestType type, @PathVariable Long userId, @PathVariable boolean status){
        LOGGER.info("LoginController.changeUserStatus() called");
        ResponseEntity<?> response = null;
        try {
            LOGGER.info("LoginController.changeUserStatus() type: " + type + " userId: " + userId + " status: " + status);
            response =  userService.changeUserStatus(type, userId, status);
            LOGGER.info("LoginController.changeUserStatus() response: " + response);
        }
        catch (Exception e) {
            LOGGER.info("LoginController.changeUserStatus() exception: " + e);
        }
        return response;
    }
}
