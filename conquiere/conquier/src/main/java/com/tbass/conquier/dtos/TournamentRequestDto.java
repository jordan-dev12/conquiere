package com.tbass.conquier.dtos;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder(toBuilder = true)
@Schema(description = "Modèle tournoi")
public record TournamentRequestDto(
		@NotBlank(message = "Le nom du tournoi est obligatoire") @Schema(description = "Le nom du tournoi", example = "CAMPIONATO") String name,

		@Schema(description = "Description du tournoi", example = "Tournoi d'été") String description,

		@Schema(description = "la date de déroulement du tournoi", example = "10-06-2025") @NotNull(message = "la date de déroulement du tournoi est obligatoire") LocalDate eventDate) {

}
