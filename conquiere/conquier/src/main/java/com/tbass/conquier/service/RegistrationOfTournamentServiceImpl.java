package com.tbass.conquier.service;

import org.springframework.stereotype.Service;

import com.tbass.conquier.dtos.RegistrationOfTournaResponseDto;
import com.tbass.conquier.dtos.RegistrationOfTournamentRequestDto;
import com.tbass.conquier.entity.TournamentEntity;
import com.tbass.conquier.entity.UserEntity;
import com.tbass.conquier.repositories.TournamentRepository;
import com.tbass.conquier.repositories.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RegistrationOfTournamentServiceImpl implements RegistrationOfTournamentService {

	private final UserRepository userRepository;
	private final TournamentRepository tournamentRepository;

	@Override
	public RegistrationOfTournaResponseDto register(RegistrationOfTournamentRequestDto registrationRequest) {

		long tournamentId = registrationRequest.getTournamentId();
		UserEntity userEntity = findUserByUsername(registrationRequest.getUsername());
		TournamentEntity tournamentEntity = findTournamentById(tournamentId);

		userEntity.registerOfTournament(tournamentEntity);
		userRepository.save(userEntity);

		return RegistrationOfTournaResponseDto.builder().message("Utilisateur bien inscrit au tournoi").build();
	}

	@Override
	public RegistrationOfTournaResponseDto unRegister(RegistrationOfTournamentRequestDto registrationRequest) {

		long tournamentId = registrationRequest.getTournamentId();
		UserEntity userEntity = findUserByUsername(registrationRequest.getUsername());
		TournamentEntity tournamentEntity = findTournamentById(tournamentId);

		userEntity.unRegisterOfTournament(tournamentEntity);
		userRepository.save(userEntity);

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
