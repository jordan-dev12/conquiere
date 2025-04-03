package com.tbass.conquier.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tbass.conquier.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

	UserEntity findByEmail(String email);

}
