package com.tbass.conquier.dtos;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TournamentResponseDto {

	private long id;

	private String name;

	private String description;

	private LocalDate dateIssued;

	private LocalDate eventDate;

	private long adminId;

}
