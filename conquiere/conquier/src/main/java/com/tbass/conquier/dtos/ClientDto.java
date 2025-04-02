package com.tbass.conquier.dtos;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientDto {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull(message = "the name must not be null")
	private String name;

	private String surname;

	@NotNull(message = "the birthdate must not be null")
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate birthdate;

}
