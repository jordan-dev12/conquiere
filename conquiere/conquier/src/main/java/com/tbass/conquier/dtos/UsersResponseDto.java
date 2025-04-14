package com.tbass.conquier.dtos;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(description = "Modèle liste utilisateurs")
@Builder(toBuilder = true)
public record UsersResponseDto(

		@Schema(description = "Liste utilisateur") List<UserRegistrationResponseDto> users,

		@Schema(description = "Nombres totals d'utilisateurs retournées") int totals) {
}
