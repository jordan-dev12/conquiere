package com.tbass.conquier.dtos;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder(toBuilder = true)
@Getter
@Setter
public class TournamentsByUserResponse {

	List<TournamentResponseDto> tournaments;

}
