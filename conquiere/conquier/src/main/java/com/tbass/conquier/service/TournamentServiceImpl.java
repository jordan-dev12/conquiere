package com.tbass.conquier.service;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.tbass.conquier.dtos.PaginationDto;
import com.tbass.conquier.dtos.TournamentRequestDto;
import com.tbass.conquier.dtos.TournamentResponseDto;
import com.tbass.conquier.dtos.Tournaments;
import com.tbass.conquier.entity.TournamentEntity;
import com.tbass.conquier.entity.UserEntity;
import com.tbass.conquier.mappers.TournamentMapper;
import com.tbass.conquier.repositories.TournamentRepository;
import com.tbass.conquier.repositories.UserRepository;
import com.tbass.conquier.utility.PaginationUtils;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TournamentServiceImpl implements TournamentService {

	private final TournamentRepository tournamentRepository;
	private final UserRepository userRepository;
	private final TournamentMapper tournamentMapper;

	@Override
	public TournamentResponseDto create(TournamentRequestDto tournamentRequestDto, String username, LocalDate currentDate) {
		UserEntity creator = userRepository.findByEmail(username)
			.orElseThrow(() -> new EntityNotFoundException("Utilisateur non trouvé avec l'username: " + username));

		TournamentEntity tournamentEntityRequest = tournamentMapper.toEntity(tournamentRequestDto);
		tournamentEntityRequest.setCreator(creator);
		tournamentEntityRequest.setDateIssued(currentDate);
		TournamentEntity tournamentEntityResponse = tournamentRepository.save(tournamentEntityRequest);
		TournamentResponseDto response = tournamentMapper.toDto(tournamentEntityResponse);
		return response;
	}

	@Override
	public TournamentResponseDto getById(long id) {

		TournamentEntity tournamentEntityResponse = tournamentRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("Tournoir non trouvé avec l'ID: " + id));
		TournamentResponseDto response = tournamentMapper.toDto(tournamentEntityResponse);
		return response;
	}

	@Override
	public void deleteAll() {

		tournamentRepository.deleteAll();

	}

	@Override
	public Tournaments loadAll(PaginationDto pagination) {

		PageRequest pageable = PaginationUtils.getPageable(pagination);
		Page<TournamentEntity> tournamentEntities = tournamentRepository.findAll(pageable);

		Tournaments tournamentsResponse = Tournaments.builder()
			.tournaments(tournamentEntities.stream().map(entity -> tournamentMapper.toDto(entity)).toList())
			.totals(tournamentEntities.getNumberOfElements())
			.build();
		return tournamentsResponse;

	}

}
