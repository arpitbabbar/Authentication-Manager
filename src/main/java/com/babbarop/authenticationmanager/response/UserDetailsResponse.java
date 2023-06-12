package com.babbarop.authenticationmanager.response;

import lombok.Data;

@Data
public class UserDetailsResponse extends AMGenericResponse {

        private Long userId;
        private String emailId;
        private String firstName;
        private String lastName;
        private String mobileNumber;
        private String address;
        private String city;
        private String state;
        private String country;
        private String pincode;
        private boolean active;
        private boolean blocked;
        private boolean deleted;
        private String createdOn;
        private String updatedOn;
}
