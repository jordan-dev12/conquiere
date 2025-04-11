package com.tbass.conquier.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tbass.conquier.dtos.RegistrationOfTournaResponseDto;
import com.tbass.conquier.dtos.RegistrationOfTournamentRequestDto;
import com.tbass.conquier.entity.RegistrationOfTournamentEntity;
import com.tbass.conquier.entity.TournamentEntity;
import com.tbass.conquier.entity.UserEntity;
import com.tbass.conquier.repositories.RegistrationOfTournamentRepository;
import com.tbass.conquier.repositories.TournamentRepository;
import com.tbass.conquier.repositories.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RegistrationOfTournamentServiceImpl implements RegistrationOfTournamentService {

	private final UserRepository userRepository;
	private final TournamentRepository tournamentRepository;
	private final RegistrationOfTournamentRepository registrationRepository;

	@Override
	@Transactional
	public RegistrationOfTournaResponseDto register(RegistrationOfTournamentRequestDto registrationRequest) {

		long tournamentId = registrationRequest.getTournamentId();
		UserEntity userEntity = findUserByUsername(registrationRequest.getUsername());
		TournamentEntity tournamentEntity = findTournamentById(tournamentId);

		if (registrationRepository.existsByUserIdAndTournamentId(userEntity.getId(), tournamentEntity.getId())) {
			throw new IllegalArgumentException("L'utilisateur est déjà inscrit à ce tournoi.");
		}

		RegistrationOfTournamentEntity registrationEntity = new RegistrationOfTournamentEntity();
		registrationEntity.setUser(userEntity);
		registrationEntity.setTournament(tournamentEntity);
		registrationEntity.setRegistrationDate(LocalDateTime.now());

		registrationRepository.save(registrationEntity);

		return RegistrationOfTournaResponseDto.builder().message("Utilisateur bien inscrit au tournoi").build();
	}

	@Override
	@Transactional
	public RegistrationOfTournaResponseDto unRegister(RegistrationOfTournamentRequestDto registrationRequest) {

		long tournamentId = registrationRequest.getTournamentId();
		UserEntity userEntity = findUserByUsername(registrationRequest.getUsername());
		TournamentEntity tournamentEntity = findTournamentById(tournamentId);

		if (!registrationRepository.existsByUserIdAndTournamentId(userEntity.getId(), tournamentEntity.getId())) {
			throw new IllegalArgumentException("L'utilisateur n'est pas inscrit à ce tournoi.");
		}

		RegistrationOfTournamentEntity registrationEntity = registrationRepository.findByUserIdAndTournamentId(userEntity.getId(), tournamentEntity.getId()).orElseThrow(() ->new EntityNotFoundException("Inscription n'on trouvé "));
		registrationRepository.deleteById(registrationEntity.getId());
		
		return RegistrationOfTournaResponseDto.builder().message("Utilisateur bien désinscrire du tournoi").build();
	}

	private UserEntity findUserByUsername(String username) {
		return userRepository.findByEmail(username)
			.orElseThrow(() -> new EntityNotFoundException("Utilisateur non trouvé avec l'usernane: " + username));
	}

	private TournamentEntity findTournamentById(long id) {
		return tournamentRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("Tournoi non trouvé avec l'ID: " + id));
	}

}
