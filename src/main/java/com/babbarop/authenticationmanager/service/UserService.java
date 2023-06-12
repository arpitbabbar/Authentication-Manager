package com.babbarop.authenticationmanager.service;

import com.babbarop.authenticationmanager.entity.UserDetails;
import com.babbarop.authenticationmanager.repository.UserCredentialsRepository;
import com.babbarop.authenticationmanager.repository.UserDetailsRepository;
import com.babbarop.authenticationmanager.request.SignUpUserRequest;
import com.babbarop.authenticationmanager.request.UpdateUserRequest;
import com.babbarop.authenticationmanager.response.AMErrorCodes;
import com.babbarop.authenticationmanager.response.AMGenericResponse;
import com.babbarop.authenticationmanager.response.SignUpUserResponse;
import com.babbarop.authenticationmanager.response.UserDetailsResponse;
import com.babbarop.authenticationmanager.util.AmEnums;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class UserService {

    private static final Logger LOGGER = Logger.getLogger(UserService.class.getName());

    private final UserDetailsRepository userDetailsRepository;

    private final UserCredentialsRepository userCredentialsRepository;

    public UserService(UserDetailsRepository userDetailsRepository, UserCredentialsRepository userCredentialsRepository) {
        this.userDetailsRepository = userDetailsRepository;
        this.userCredentialsRepository = userCredentialsRepository;
    }

    public ResponseEntity<?> signUpUser(SignUpUserRequest request) {
        ResponseEntity<?> response;
        SignUpUserResponse signUpUserResponse = new SignUpUserResponse();
        try {
            Optional<UserDetails> userDetails = userDetailsRepository.findByEmailId(request.getEmailId());
            if (userDetails.isPresent()) {
                signUpUserResponse.setStatusCode(AMErrorCodes.EMAIL_ALREADY_EXISTS);
            } else if (request.getPassword().length() < 8) {
                signUpUserResponse.setStatusCode(AMErrorCodes.PASSWORD_LENGTH);
            } else if (!request.getPassword().equals(request.getConfirmPassword())) {
                signUpUserResponse.setStatusCode(AMErrorCodes.PASSWORD_MISMATCH);
            } else {
                UserDetails user = new UserDetails();
                BeanUtils.copyProperties(request, user);
                user.setCreatedOn(new Date());
                user.setUpdatedOn(new Date());
                user.setActive(true);
                user.setBlocked(false);
                user.setDeleted(false);
                userDetailsRepository.save(user);
                signUpUserResponse.setUserId(user.getUserId());
                signUpUserResponse.setStatusCode(AMErrorCodes.SUCCESS);
            }
            response = ResponseEntity.ok().body(signUpUserResponse);
        } catch (Exception e) {
            LOGGER.info("LoginController.signUpUser() exception: " + e);
            signUpUserResponse.setStatusCode(AMErrorCodes.INTERNAL_ERROR);
            response = ResponseEntity.badRequest().body(signUpUserResponse);
        }
        return response;
    }

    public ResponseEntity<?> updateUser(UpdateUserRequest request) {
        ResponseEntity<?> response;
        SignUpUserResponse signUpUserResponse = new SignUpUserResponse();
        try {
            Optional<UserDetails> userDetails = userDetailsRepository.findByUserId(request.getUserId());
            if (userDetails.isPresent()) {
                if (request.getPassword().length() < 8) {
                    signUpUserResponse.setStatusCode(AMErrorCodes.PASSWORD_LENGTH);
                    return ResponseEntity.ok().body(signUpUserResponse);
                } else if (!request.getPassword().equals(request.getConfirmPassword())) {
                    signUpUserResponse.setStatusCode(AMErrorCodes.PASSWORD_MISMATCH);
                    return ResponseEntity.ok().body(signUpUserResponse);
                }
                UserDetails user = userDetails.get();
                BeanUtils.copyProperties(request, user);
                user.setUpdatedOn(new Date());
                userDetailsRepository.save(user);
                signUpUserResponse.setUserId(user.getUserId());
                signUpUserResponse.setStatusCode(AMErrorCodes.USER_PROFILE_UPDATED);
            } else {
                signUpUserResponse.setStatusCode(AMErrorCodes.USER_NOT_FOUND);
            }
            response = ResponseEntity.ok().body(signUpUserResponse);
        } catch (Exception e) {
            LOGGER.info("LoginController.updateUser() exception: " + e);
            signUpUserResponse.setStatusCode(AMErrorCodes.INTERNAL_ERROR);
            response = ResponseEntity.badRequest().body(signUpUserResponse);
        }
        return response;
    }

    public ResponseEntity<?> deleteUser(Long userId) {
        ResponseEntity<?> response;
        AMGenericResponse amGenericResponse = new AMGenericResponse();
        try {
            Optional<UserDetails> userDetails = userDetailsRepository.findByUserId(userId);
            if (userDetails.isPresent()) {
                UserDetails user = userDetails.get();
                user.setDeleted(true);
                userDetailsRepository.save(user);
                amGenericResponse.setStatusCode(AMErrorCodes.USER_PROFILE_DELETED);
            } else {
                amGenericResponse.setStatusCode(AMErrorCodes.USER_NOT_FOUND);
            }
            response = ResponseEntity.ok().body(amGenericResponse);
        } catch (Exception e) {
            LOGGER.info("LoginController.deleteUser() exception: " + e);
            amGenericResponse.setStatusCode(AMErrorCodes.INTERNAL_ERROR);
            response = ResponseEntity.badRequest().body(amGenericResponse);
        }
        return response;
    }

    public ResponseEntity<?> getUserDetails(Long userId) {
        ResponseEntity<?> response;
        UserDetailsResponse userDetailsResponse = new UserDetailsResponse();
        try {
            Optional<UserDetails> userDetails = userDetailsRepository.findByUserId(userId);
            if (userDetails.isPresent()) {
                UserDetails user = userDetails.get();
                BeanUtils.copyProperties(user, userDetailsResponse);
                userDetailsResponse.setStatusCode(AMErrorCodes.SUCCESS);
            } else {
                userDetailsResponse.setStatusCode(AMErrorCodes.USER_NOT_FOUND);
            }
            response = ResponseEntity.ok().body(userDetailsResponse);
        } catch (Exception e) {
            LOGGER.info("LoginController.getUserDetails() exception: " + e);
            userDetailsResponse.setStatusCode(AMErrorCodes.INTERNAL_ERROR);
            response = ResponseEntity.badRequest().body(userDetailsResponse);
        }
        return response;
    }

    public ResponseEntity<?> changeUserStatus(AmEnums.StatusRequestType type, Long userId, boolean status) {
        ResponseEntity<?> response;
        AMGenericResponse amGenericResponse = new AMGenericResponse();
        try {
            Optional<UserDetails> userDetails = userDetailsRepository.findByUserId(userId);
            if (userDetails.isPresent()) {
                UserDetails user = userDetails.get();
                switch (type) {
                    case ACTIVE:
                        user.setActive(status);
                        break;
                    case BLOCKED:
                        user.setBlocked(status);
                        break;
                    case DELETED:
                        user.setDeleted(status);
                        break;
                }
                userDetailsRepository.save(user);
                amGenericResponse.setStatusCode(AMErrorCodes.USER_STATUS_UPDATED);
            } else {
                amGenericResponse.setStatusCode(AMErrorCodes.USER_NOT_FOUND);
            }
            response = ResponseEntity.ok().body(amGenericResponse);
        } catch (Exception e) {
            LOGGER.info("LoginController.changeUserStatus() exception: " + e);
            amGenericResponse.setStatusCode(AMErrorCodes.INTERNAL_ERROR);
            response = ResponseEntity.badRequest().body(amGenericResponse);
        }
        return response;
    }
}