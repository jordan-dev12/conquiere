package com.tbass.conquier.service;

import com.tbass.conquier.dtos.RegistrationOfTournaResponseDto;
import com.tbass.conquier.dtos.RegistrationOfTournamentRequestDto;

public interface RegistrationOfTournamentService {

	public RegistrationOfTournaResponseDto register(RegistrationOfTournamentRequestDto registrationRequest);

	public RegistrationOfTournaResponseDto unRegister(RegistrationOfTournamentRequestDto registrationRequest);

}
