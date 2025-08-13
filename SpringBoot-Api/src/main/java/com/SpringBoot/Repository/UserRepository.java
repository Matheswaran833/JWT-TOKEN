package com.SpringBoot.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.SpringBoot.Entity.UserEntity;

public interface UserRepository extends JpaRepository <UserEntity,Long>{
	Optional<UserEntity> findByUsername(String username);

}
