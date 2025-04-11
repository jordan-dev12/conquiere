package com.tbass.conquier.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tbass.conquier.dtos.RegistrationOfTournaResponseDto;
import com.tbass.conquier.dtos.RegistrationOfTournamentRequestDto;
import com.tbass.conquier.service.RegistrationOfTournamentService;
import com.tbass.conquier.utility.AuthentificationUtilis;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/regis")
@Tag(name = "Inscriptions aux tournois", description = "API pour gérer les inscriptions des utilisateurs aux tournois")
public class RegistrationOfTournamentController {

	private final RegistrationOfTournamentService regisService;
	private final AuthentificationUtilis auth;

	@PostMapping("/register/{tournoiId}")
	@Operation(summary = "Inscrire l'utilisateur connecté à un tournoi", description = "Permet à l'utilisateur actuellement de s'inscrire à un tournoi spécifique")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Inscription réussie", content = @Content(schema = @Schema(implementation = RegistrationOfTournaResponseDto.class))),
			@ApiResponse(responseCode = "400", description = "Utilisateur déjà inscrit"),
			@ApiResponse(responseCode = "404", description = "Tournoi ou utilisateur non trouvé"),
			@ApiResponse(responseCode = "403", description = "Accès refusé")
	})
	public RegistrationOfTournaResponseDto registerOfTournament(@PathVariable @NotNull @Positive long tournoiId) {

		String username = auth.getCurrentUsername();

		RegistrationOfTournamentRequestDto request = RegistrationOfTournamentRequestDto.builder().username(username).tournamentId(tournoiId).build();
		return regisService.register(request);

	}

	@DeleteMapping("/unregister/{tournoiId}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	@Operation(summary = "Désinscrire l'utilisateur d'un tournoi", description = "Permet à l'utilisateur actuellement authentifié de se désinscrire d'un tournoi")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Désinscription réussie", content = @Content(schema = @Schema(implementation = RegistrationOfTournaResponseDto.class))),
			@ApiResponse(responseCode = "404", description = "Tournoi ou utilisateur non trouvé"),
			@ApiResponse(responseCode = "400", description = "Utilisateur non inscrit à ce tournoi"),
			@ApiResponse(responseCode = "403", description = "Accès refusé")
	})
	public RegistrationOfTournaResponseDto unRegisterOfTournament(@PathVariable @NotNull @Positive long tournoiId) {

		String username = auth.getCurrentUsername();

		RegistrationOfTournamentRequestDto request = RegistrationOfTournamentRequestDto.builder().username(username).tournamentId(tournoiId).build();
		return regisService.unRegister(request);

	}

}
