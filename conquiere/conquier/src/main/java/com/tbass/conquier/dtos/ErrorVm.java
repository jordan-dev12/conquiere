package com.tbass.conquier.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ErrorVm {

	private String message;
	private int statusCode;
	private String descrizione;

}
