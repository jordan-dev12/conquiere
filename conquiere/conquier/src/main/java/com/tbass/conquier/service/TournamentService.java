package com.tbass.conquier.service;

import java.time.LocalDate;

import com.tbass.conquier.dtos.TournamentRequestDto;
import com.tbass.conquier.dtos.TournamentResponseDto;

public interface TournamentService {

	public TournamentResponseDto create(TournamentRequestDto tournament, String usernameAdmin, LocalDate currentDate);

	public TournamentResponseDto getById(long id);

	public void deleteAll();

}
