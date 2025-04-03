package com.tbass.conquier.dtos;

import java.time.LocalDate;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegistrationResponseDto {

	private Long id;

	private String name;

	private String surname;

	private String email;

	@JsonFormat(pattern = "dd-MM-yyyy")
	private LocalDate birthdate;

	private ArrayList<String> roles;

}
