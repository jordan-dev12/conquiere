package com.tbass.conquier.service;

import com.tbass.conquier.dtos.TournamentRequestDto;
import com.tbass.conquier.dtos.TournamentResponseDto;

public interface TournamentService {

	public TournamentResponseDto create(TournamentRequestDto tournament);

	public TournamentResponseDto getById(long id);

}
