package com.tbass.conquier.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tbass.conquier.dtos.PaginationDto;
import com.tbass.conquier.dtos.UserRegistrationRequestDto;
import com.tbass.conquier.dtos.UserRegistrationResponseDto;
import com.tbass.conquier.dtos.UsersResponseDto;
import com.tbass.conquier.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;

@Validated
@RestController
@RequestMapping(value = "/api")
@Tag(name = "Utilisateurs", description = "API de gestion des utilisateurs")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping(value = "/register")
	@ResponseStatus(HttpStatus.OK)
	@Operation(summary = "Créer un utilisateur", description = "Crée un nouvel utilisateur dans le système", responses = {
			@ApiResponse(responseCode = "200", description = "Utilisateur créé avec succès", content = @Content(schema = @Schema(implementation = UserRegistrationResponseDto.class))),
			@ApiResponse(responseCode = "400", description = "Données d'utilisateur invalides", content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
			@ApiResponse(responseCode = "409", description = "Un utilisateur avec cet email existe déjà", content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = ProblemDetail.class))) })
	public UserRegistrationResponseDto save(@RequestBody @Valid UserRegistrationRequestDto request) {
		return userService.save(request);
	}

	@PostMapping(value = "/all")
	@ResponseStatus(HttpStatus.OK)
	@Operation(summary = "Lister tous les utilisateurs", description = "Récupère la liste de tous les utilisateurs avec pagination", responses = {
			@ApiResponse(responseCode = "200", description = "Liste des utilisateurs récupérée avec succès", content = @Content(schema = @Schema(implementation = UsersResponseDto.class))),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = ProblemDetail.class))) })
	public UsersResponseDto getAll(@RequestBody @Valid PaginationDto pagination) {
		return userService.getClients(pagination);
	}

	@GetMapping(value = "/get/{id}")
	@ResponseStatus(HttpStatus.OK)
	@Operation(summary = "Récupérer un utilisateur par ID", description = "Retourne un utilisateur en fonction de son identifiant", responses = {
			@ApiResponse(responseCode = "200", description = "Utilisateur trouvé", content = @Content(schema = @Schema(implementation = UserRegistrationResponseDto.class))),
			@ApiResponse(responseCode = "404", description = "Utilisateur non trouvé", content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = ProblemDetail.class))) })
	public UserRegistrationResponseDto getById(@PathVariable @Positive Long id) {
		return userService.getById(id);
	}

	@DeleteMapping("/delete/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@Operation(summary = "Supprimer un utilisateur", description = "Supprime un utilisateur du système", responses = {
			@ApiResponse(responseCode = "204", description = "Utilisateur supprimé avec succès"),
			@ApiResponse(responseCode = "404", description = "Utilisateur non trouvé", content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
			@ApiResponse(responseCode = "403", description = "Opération non autorisée", content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = ProblemDetail.class))) })
	public void deleteById(@PathVariable @Positive Long id) {
		userService.delete(id);
	}

}
