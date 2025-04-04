package com.tbass.conquier.dtos;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Modèle de registration d'un utilisateur")
public class UserRegistrationRequestDto {

	@Schema(description = "Nom de l'utilisateur", example = "Jean")
	@NotBlank(message = "Le nom est obligatoire")
	private String name;

	@Schema(description = "Prénom de l'utilisateur", example = "Luc")
	@NotBlank(message = "Le prénom est obligatoire")
	private String surname;

	@Schema(description = "L'email de l'utilisateur", example = "xff.fe@gmail.com")
	@NotBlank(message = "L'email est obligatoire")
	private String email;

	@Schema(description = "Mot de passe de l'utilisateur")
	@NotBlank(message = "Mot de passe est obligatoire")
	private String password;

	@NotNull(message = "la date d'anniversaire est obbligatoire")
	@Schema(description = "Date d'anniversaire", example = "10-02-1991")
	private LocalDate birthdate;

}
