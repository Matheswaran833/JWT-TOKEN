package com.userdatabase;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLoginRepository extends JpaRepository<UserLogin, Long> {
    Optional<UserLogin> findByUsername(String username);
}