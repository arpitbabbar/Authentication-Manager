package com.babbarop.authenticationmanager.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "otp_user")
public class OtpUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "email_id", unique = true)
    private String emailId;
    private String otp;
    @Column(name = "created_on")
    private Date createdOn;
    @Column(name = "updated_on")
    private Date updatedOn;
    @Column(name = "otp_attempts")
    private Integer otpAttempts;
    private Boolean blocked;

}
