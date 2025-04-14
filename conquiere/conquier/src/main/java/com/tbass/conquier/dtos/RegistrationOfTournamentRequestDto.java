package com.tbass.conquier.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder(toBuilder = true)
public record RegistrationOfTournamentRequestDto(@NotNull String username, @NotNull Long tournamentId) {
}
