package com.userdatabase;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDetailsRepository extends JpaRepository<UserDetails, Long> {
    Optional<UserDetails> findByUsername(String username);
}
