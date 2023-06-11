package com.babbarop.authenticationmanager.repository;

import com.babbarop.authenticationmanager.entity.OtpUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OtpUserRepository extends JpaRepository<OtpUser, Long> {


    Optional<OtpUser> findByEmailId(String emailId);
}
