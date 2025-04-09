package com.tbass.conquier.dtos;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder(toBuilder = true)
@Schema(description = "Modèle tournoi")
public class TournamentRequestDto {

	@NotBlank(message = "Le nom du tournoi est obligatoire")
	@Schema(description = "Le nom du tournoi", example = "CAMPIONATO")
	private String name;

	@Schema(description = "Description du tournoi", example = "Tournoi d'été")
	private String description;

	@Schema(description = "la date de déroulement du tournoi", example = "10-06-2025")
	@NotNull(message = "la date de déroulement du tournoi est obligatoire")
	private LocalDate eventDate;

}
