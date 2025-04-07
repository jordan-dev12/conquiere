package com.tbass.conquier.service;

import org.springframework.stereotype.Service;

import com.tbass.conquier.dtos.TournamentRequestDto;
import com.tbass.conquier.dtos.TournamentResponseDto;
import com.tbass.conquier.entity.TournamentEntity;
import com.tbass.conquier.entity.UserEntity;
import com.tbass.conquier.mappers.TournamentMapper;
import com.tbass.conquier.repositories.TournamentRepository;
import com.tbass.conquier.repositories.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class TournamentServiceImpl implements TournamentService {

	private final TournamentRepository tournamentRepository;
	private final UserRepository userRepository;
	private final TournamentMapper tournamentMapper;

	public TournamentServiceImpl(TournamentRepository tournamentRepository, UserRepository uersRepository, TournamentMapper tournamentMapper) {
		this.tournamentRepository = tournamentRepository;
		this.userRepository = uersRepository;
		this.tournamentMapper = tournamentMapper;
	}

	@Override
	public TournamentResponseDto create(TournamentRequestDto tournament, String username) {
		UserEntity creator = userRepository.findByEmail(username).orElseThrow(() -> new EntityNotFoundException("Utilisateur non trouvé avec l'username: " + username));

		TournamentEntity tournamentEntityRequest = tournamentMapper.toEntity(tournament);
		tournamentEntityRequest.setCreator(creator);

		TournamentEntity tournamentEntityResponse = tournamentRepository.save(tournamentEntityRequest);
		TournamentResponseDto response = tournamentMapper.toDto(tournamentEntityResponse);
		response.setAdminId(tournamentEntityResponse.getCreator().getId());
		return response;
	}

	@Override
	public TournamentResponseDto getById(long id) {

		TournamentEntity tournamentEntityResponse = tournamentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Tournoir non trouvé avec l'ID: " + id));
		TournamentResponseDto response = tournamentMapper.toDto(tournamentEntityResponse);
		response.setAdminId(tournamentEntityResponse.getCreator().getId());
		return response;
	}

}
