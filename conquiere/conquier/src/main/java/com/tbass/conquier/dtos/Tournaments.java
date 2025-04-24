package com.tbass.conquier.dtos;

import java.util.List;

import lombok.Builder;

@Builder(toBuilder = true)
public record Tournaments(List<TournamentResponseDto> tournaments, int totals) {

}
