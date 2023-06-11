package com.babbarop.authenticationmanager.util;

import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Random;

/**
 * @author Arpit Babbar
 * @since 2023-06-12
 * @implNote Utility class for Authentication Manager
 */
@Component
public class Utility {

    public static final Random random = new Random();

    public static String generateOtp() {
        int randomPin   = random.nextInt(9000)+1000;
        return String.valueOf(randomPin);
    }


    public static long getTimeDifference(Date updatedOn) {
        Date currentDate = new Date();
       return (currentDate.getTime() - updatedOn.getTime()) / (60 * 1000) % 60;

    }
}
