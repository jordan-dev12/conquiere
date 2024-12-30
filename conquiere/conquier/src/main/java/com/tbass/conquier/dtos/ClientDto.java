package com.tbass.conquier.dtos;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientDto {

	private Long id;

	@NotNull(message = "the name must not be null")
	private String name;

	private String surname;

	@NotNull(message = "the birthdate must not be null")
	private LocalDate birthdate;

}
