package com.babbarop.authenticationmanager.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "user_credentials")
public class UserCredentials {

    @Id
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "email_id", unique = true)
    private String emailId;
    @Column(name = "password")
    private String password;

}
