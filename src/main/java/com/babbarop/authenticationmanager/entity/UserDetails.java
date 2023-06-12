package com.babbarop.authenticationmanager.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "user_details")
public class UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "email_id", unique = true)
    private String emailId;
    private String password;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "mobile_number", unique = true)
    private String mobileNumber;
    private String address;
    private String city;
    private String state;
    private String country;
    private String pincode;
    private boolean active;
    private boolean blocked;
    private boolean deleted;
    @Column(name = "created_on")
    private Date createdOn;
    @Column(name = "updated_on")
    private Date updatedOn;


}
