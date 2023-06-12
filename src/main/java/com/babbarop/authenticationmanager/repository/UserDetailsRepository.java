package com.babbarop.authenticationmanager.repository;

import com.babbarop.authenticationmanager.entity.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetails, Long> {

    Optional<UserDetails> findByEmailId(String emailId);

    Optional<UserDetails> findByUserId(Long userId);
}
