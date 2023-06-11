package com.babbarop.authenticationmanager.service;

import com.babbarop.authenticationmanager.entity.OtpUser;
import com.babbarop.authenticationmanager.entity.UserCredentials;
import com.babbarop.authenticationmanager.repository.OtpUserRepository;
import com.babbarop.authenticationmanager.repository.UserCredentialsRepository;
import com.babbarop.authenticationmanager.request.LoginUserEmailRequest;
import com.babbarop.authenticationmanager.request.LoginUserOtpRequest;
import com.babbarop.authenticationmanager.response.AMErrorCodes;
import com.babbarop.authenticationmanager.response.GenerateOtpResponse;
import com.babbarop.authenticationmanager.response.LoginUserEmailResponse;
import com.babbarop.authenticationmanager.response.LoginUserOtpResponse;
import com.babbarop.authenticationmanager.util.Utility;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * @author Arpit Babbar
 * @since 2023-06-12
 */
@Service
public class LoginService {

    private static final Logger LOGGER = Logger.getLogger(LoginService.class.getName());

    final
    UserCredentialsRepository userCredentialsRepository;

    final
    OtpUserRepository otpUserRepository;


    public LoginService(UserCredentialsRepository userCredentialsRepository, OtpUserRepository otpUserRepository) {
        this.userCredentialsRepository = userCredentialsRepository;
        this.otpUserRepository = otpUserRepository;
    }


    public ResponseEntity<?> loginUser(LoginUserEmailRequest request) {
        ResponseEntity<?> response;
        LoginUserEmailResponse loginUserEmailResponse = new LoginUserEmailResponse();
        try {
            Optional<UserCredentials> user = userCredentialsRepository.findByEmailId(request.getEmail());
            LOGGER.info("LoginService.loginUser() user: " + user);
            if(user.isPresent()) {
                if(user.get().getPassword().equals(request.getPassword())) {
                    loginUserEmailResponse.setUserId(user.get().getUserId());
                    loginUserEmailResponse.setStatusCode(AMErrorCodes.SUCCESS);
                    response = ResponseEntity.ok().body(loginUserEmailResponse);
                }
                else {
                    loginUserEmailResponse.setStatusCode(AMErrorCodes.PASSWORD_MISMATCH);
                    response = ResponseEntity.badRequest().body(loginUserEmailResponse);
                }
            }
            else {
                loginUserEmailResponse.setStatusCode(AMErrorCodes.INVALID_EMAIL);
                response = ResponseEntity.badRequest().body(loginUserEmailResponse);
            }

        }
        catch (Exception e) {
            LOGGER.info("LoginController.loginUser() exception: " + e);
            loginUserEmailResponse.setStatusCode(AMErrorCodes.INTERNAL_ERROR);
            response = ResponseEntity.badRequest().body(loginUserEmailResponse);
        }
        return response;
    }

    public ResponseEntity<?> generateOtp(String emailId) {
        ResponseEntity<?> response;
        GenerateOtpResponse generateOtpResponse = new GenerateOtpResponse();
        try {
            Optional<OtpUser> user = otpUserRepository.findByEmailId(emailId);
            if(user.isPresent()) {

                if(user.get().isBlocked()){
                    generateOtpResponse.setStatusCode(AMErrorCodes.USER_BLOCKED);
                    response = ResponseEntity.ok().body(generateOtpResponse);
                    return response;
                }

                Date currentDate = new Date();
                LOGGER.info("LoginService.generateOtp() currentDate: " + currentDate);
                LOGGER.info("LoginService.generateOtp() user.get().getUpdatedOn(): " + user.get().getUpdatedOn());
                long diff = currentDate.getTime() - user.get().getUpdatedOn().getTime();
                LOGGER.info("LoginService.generateOtp() diff: " + diff);
                long diffSeconds = diff / 1000 % 60;
                LOGGER.info("LoginService.generateOtp() diffSeconds: " + diffSeconds);
                if(diffSeconds < 30) {
                    generateOtpResponse.setStatusCode(AMErrorCodes.WAIT_OTP);
                    response = ResponseEntity.ok().body(generateOtpResponse);
                    return response;
                }

                user.get().setOtp(Utility.generateOtp());
                user.get().setUpdatedOn(new Date());
                user.get().setOtpAttempts(0);
                otpUserRepository.save(user.get());
                generateOtpResponse.setOtp(user.get().getOtp());
            }
            else {
                OtpUser otpUser = new OtpUser();
                otpUser.setEmailId(emailId);
                otpUser.setOtp(Utility.generateOtp());
                otpUser.setCreatedOn(new Date());
                otpUser.setUpdatedOn(new Date());
                otpUser.setOtpAttempts(0);
                otpUser.setBlocked(false);
                otpUserRepository.save(otpUser);
                generateOtpResponse.setOtp(otpUser.getOtp());
            }
            generateOtpResponse.setStatusCode(AMErrorCodes.OTP_GENERATED);
            response = ResponseEntity.ok().body(generateOtpResponse);
        }
        catch (Exception e) {
            LOGGER.info("LoginController.generateOtp() exception: " + e);
            generateOtpResponse.setStatusCode(AMErrorCodes.INTERNAL_ERROR);
            response = ResponseEntity.badRequest().body(generateOtpResponse);
        }
        return response;
    }

    public ResponseEntity<?> loginUserOtp(LoginUserOtpRequest request) {
        ResponseEntity<?> response = null;
        LoginUserOtpResponse loginUserOtpResponse = new LoginUserOtpResponse();
        try {
            Optional<OtpUser> user = otpUserRepository.findByEmailId(request.getEmail());
            if(user.isPresent()) {
                long diffMinutes = Utility.getTimeDifference(user.get().getUpdatedOn());
                if(user.get().isBlocked()){
                    loginUserOtpResponse.setStatusCode(AMErrorCodes.USER_BLOCKED);
                    response = ResponseEntity.ok().body(loginUserOtpResponse);
                    return response;
                }
                else if (diffMinutes >=1){
                    loginUserOtpResponse.setStatusCode(AMErrorCodes.OTP_EXPIRED);
                    response = ResponseEntity.ok().body(loginUserOtpResponse);
                    return response;
                }
               else if(user.get().getOtp().equals(request.getOtp())) {
                    loginUserOtpResponse.setUserId(user.get().getId());
                    loginUserOtpResponse.setStatusCode(AMErrorCodes.SUCCESS);
                }
                else {
                    int otpAttempts = user.get().getOtpAttempts();
                    if(otpAttempts >= 3) {
                        user.get().setBlocked(true);
                        otpUserRepository.save(user.get());
                        loginUserOtpResponse.setStatusCode(AMErrorCodes.USER_BLOCKED);
                    }
                    else {
                        user.get().setOtpAttempts(otpAttempts + 1);
                        otpUserRepository.save(user.get());
                        loginUserOtpResponse.setStatusCode(AMErrorCodes.OTP_MISMATCH);
                    }
                }
                response = ResponseEntity.ok().body(loginUserOtpResponse);
            }
            else {
                loginUserOtpResponse.setStatusCode(AMErrorCodes.INVALID_EMAIL);
                response = ResponseEntity.badRequest().body(loginUserOtpResponse);
            }
        }
        catch (Exception e) {
            LOGGER.info("LoginController.loginUser() exception: " + e);
        }
        return response;
    }
}
