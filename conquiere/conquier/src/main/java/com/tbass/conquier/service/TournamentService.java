package com.tbass.conquier.service;

import java.time.LocalDate;

import com.tbass.conquier.dtos.PaginationDto;
import com.tbass.conquier.dtos.TournamentRequestDto;
import com.tbass.conquier.dtos.TournamentResponseDto;
import com.tbass.conquier.dtos.Tournaments;

public interface TournamentService {

	public TournamentResponseDto create(TournamentRequestDto tournament, String usernameAdmin, LocalDate currentDate);

	public TournamentResponseDto getById(long id);

	public Tournaments getAll(PaginationDto page);

	public void deleteAll();

}
