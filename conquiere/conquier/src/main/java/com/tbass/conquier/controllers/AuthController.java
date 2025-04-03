package com.tbass.conquier.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tbass.conquier.dtos.AuthRequestDto;
import com.tbass.conquier.dtos.AuthResponseDto;
import com.tbass.conquier.security.JwtUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api")
@Tag(name = "Authentification", description = "API de gestion des authentifications")
public class AuthController {

	private final AuthenticationManager authenticationManager;
	private final UserDetailsService userDetailsService;
	private final JwtUtil jwtUtil;

	public AuthController(AuthenticationManager authenticationManager, UserDetailsService userDetailsService, JwtUtil jwtUtil) {
		this.authenticationManager = authenticationManager;
		this.userDetailsService = userDetailsService;
		this.jwtUtil = jwtUtil;
	}

	@PostMapping("/auth")
	@ResponseStatus(HttpStatus.OK)
	@Operation(summary = "Authentification  d'un utilisateur", description = "Permet d'authentifier un utilisateur dans le système", responses = {
			@ApiResponse(responseCode = "200", description = "Utilisateur authentifier avec succès", content = @Content(schema = @Schema(implementation = AuthResponseDto.class))),
			@ApiResponse(responseCode = "401", description = "Credentials invalides", content = @Content(schema = @Schema(implementation = BadCredentialsException.class))),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = ProblemDetail.class))) })
	public AuthResponseDto createAuthenticationToken(@RequestBody AuthRequestDto auth) throws Exception {

		authenticate(auth.username(), auth.password());

		final UserDetails userDetails = userDetailsService.loadUserByUsername(auth.username());

		final String token = jwtUtil.generateToken(userDetails, auth.username());

		return new AuthResponseDto(token);
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new DisabledException("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new BadCredentialsException("INVALID_CREDENTIALS", e);
		} catch (Exception e) {
			throw new Exception("GENERIC ERROR", e);
		}
	}

}
