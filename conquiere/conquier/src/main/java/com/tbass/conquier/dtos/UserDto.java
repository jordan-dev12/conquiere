package com.tbass.conquier.dtos;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Modèle d'un utilisateur")
public class UserDto {

	@Schema(description = "Identifiant unique", example = "1")
	private Long id;

	@Schema(description = "Nom de l'utilisateur", example = "Jean")
	@NotNull(message = "the name must not be null")
	private String name;

	@Schema(description = "Prénom de l'utilisateur", example = "Luc")
	private String surname;

	@NotNull(message = "the birthdate must not be null")
	@Schema(description = "Date d'anniversaire", example = "10-02-1991")
	@JsonFormat(pattern = "dd-MM-yyyy")
	private LocalDate birthdate;

}
