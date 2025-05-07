package com.tbass.conquier.dtos;

import java.time.LocalDate;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;

@Builder(toBuilder = true)
public record UserRegistrationResponseDto(
		Long id,

		String name,

		String surname,

		String email,
		Boolean isActivated,

		@JsonFormat(pattern = "dd-MM-yyyy") LocalDate birthdate,

		ArrayList<String> roles)

{
}
