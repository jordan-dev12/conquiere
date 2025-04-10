package com.tbass.conquier.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder(toBuilder = true)
public class RegistrationOfTournamentRequestDto {

	private String username;
	private long tournamentId;

}
