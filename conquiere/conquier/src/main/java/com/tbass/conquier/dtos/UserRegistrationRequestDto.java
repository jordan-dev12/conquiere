package com.tbass.conquier.dtos;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder(toBuilder = true)
@Schema(description = "Modèle de registration d'un utilisateur")
public record UserRegistrationRequestDto(

		@Schema(description = "Nom de l'utilisateur", example = "Jean") @NotBlank(message = "Le nom est obligatoire") String name,

		@Schema(description = "Prénom de l'utilisateur", example = "Luc") @NotBlank(message = "Le prénom est obligatoire") String surname,

		@Schema(description = "L'email de l'utilisateur", example = "xff.fe@gmail.com") @NotBlank(message = "L'email est obligatoire") @Email(message = "L'adresse e-mail doit être valide") String email,

		@Schema(description = "Mot de passe de l'utilisateur") @NotBlank(message = "Mot de passe est obligatoire") String password,

		@NotNull(message = "la date d'anniversaire est obbligatoire") @Schema(description = "Date d'anniversaire", example = "10-02-1991") LocalDate birthdate)

{

}
