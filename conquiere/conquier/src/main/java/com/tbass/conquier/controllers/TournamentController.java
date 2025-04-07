package com.tbass.conquier.controllers;

import java.time.LocalDate;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tbass.conquier.dtos.TournamentRequestDto;
import com.tbass.conquier.dtos.TournamentResponseDto;
import com.tbass.conquier.service.TournamentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin")
@Tag(name = "Tournois", description = "API pour la gestion des tournois")
@RequiredArgsConstructor
public class TournamentController {

	private final TournamentService tournamentService;

	@PostMapping(value = "/createTournament")
	@ResponseStatus(HttpStatus.CREATED)
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary = "Créer un nouveau tournoi", description = "Crée un nouveau tournoi avec les informations fournies. Seuls les administrateurs peuvent créer des tournois.")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Tournoi créé avec succès", content = @Content(schema = @Schema(implementation = TournamentResponseDto.class))),
			@ApiResponse(responseCode = "400", description = "Données de tournoi invalides"),
			@ApiResponse(responseCode = "403", description = "Accès refusé - L'utilisateur n'est pas un administrateur"),
			@ApiResponse(responseCode = "404", description = "Utilisateur créateur non trouvé") })
	public TournamentResponseDto create(@RequestBody @Valid TournamentRequestDto tournamentDTO) {

		LocalDate currentDate = LocalDate.now();
		if (tournamentDTO.getEventDate().isBefore(currentDate)) {
			throw new IllegalArgumentException("La date du tournoi doit être postérieure ou égale à la date d'aujourd'hui");
		}

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return tournamentService.create(tournamentDTO, authentication.getName(), currentDate);
	}

	@Operation(summary = "Récupérer un tournoi par son ID", description = "Récupère les détails complets d'un tournoi")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Tournoi trouvé", content = @Content(schema = @Schema(implementation = TournamentResponseDto.class))),
			@ApiResponse(responseCode = "404", description = "Tournoi non trouvé") })
	@GetMapping("/getTournament/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public TournamentResponseDto getTournamentById(@Positive @PathVariable Long id) {

		TournamentResponseDto tournament = tournamentService.getById(id);
		return tournament;
	}

}
