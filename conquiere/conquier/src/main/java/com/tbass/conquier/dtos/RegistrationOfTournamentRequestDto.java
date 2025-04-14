package com.tbass.conquier.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder(toBuilder = true)
public record RegistrationOfTournamentRequestDto(@NotBlank String username, @NotBlank Long tournamentId) {
}
