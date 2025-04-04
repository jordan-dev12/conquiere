package com.tbass.conquier.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tbass.conquier.entity.TournamentEntity;

public interface TournamentRepository extends JpaRepository<TournamentEntity, Long> {

}
