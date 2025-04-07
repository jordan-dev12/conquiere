package com.tbass.conquier.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tbass.conquier.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

	Optional<UserEntity> findByEmail(String email);

}
