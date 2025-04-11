package com.tbass.conquier.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tbass.conquier.entity.RegistrationOfTournamentEntity;

public interface RegistrationOfTournamentRepository extends JpaRepository<RegistrationOfTournamentEntity, Long> {

	boolean existsByUserIdAndTournamentId(@Param("userId") Long userId, @Param("tournamentId") Long tournamentId);

	@Modifying
	@Query("DELETE FROM RegistrationOfTournamentEntity ru WHERE ru.user.id = :userId AND ru.tournament.id = :tournamentId")
	void deleteByUserIdAndTournamentId(@Param("userId") Long userId, @Param("tournamentId") Long tournamentId);

	Optional<RegistrationOfTournamentEntity> findByUserIdAndTournamentId(@Param("userId") Long userId, @Param("tournamentId") Long tournamentId);

}
