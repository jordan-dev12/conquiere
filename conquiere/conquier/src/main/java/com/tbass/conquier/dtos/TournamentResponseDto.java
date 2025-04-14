package com.tbass.conquier.dtos;

import java.time.LocalDate;

import lombok.Builder;

@Builder
public record TournamentResponseDto(

		Long id,

		String name,

		String description,

		LocalDate dateIssued,

		LocalDate eventDate,

		Long adminId) {
}
