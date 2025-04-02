package com.tbass.conquier.common;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.tbass.conquier.dtos.ErrorVm;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExecption {

	private static String NOT_VALID_EXCEPTION = "ERREUR DE VALIDATION - ";
	private static String NO_RESOURCE_FOUND_EXCEPTION = "ERREUR DE VALIDATION - ";
	private static String GENERIC_EXCEPTION = "ERREUR GENERICQUE - ";

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ErrorVm handleInvalidArgument(MethodArgumentNotValidException ex, WebRequest request) {
		log.error(NOT_VALID_EXCEPTION + ex.getMessage(), ex);
		return ErrorVm.builder().descrizione(request.getDescription(false)).message(ex.getMessage())
				.statusCode(HttpStatus.BAD_REQUEST.value()).build();

	}
	@ExceptionHandler(NoResourceFoundException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ErrorVm handleNoResourceFoundException(NoResourceFoundException ex, WebRequest request) {
		log.error(NO_RESOURCE_FOUND_EXCEPTION + ex.getMessage(), ex);
		return ErrorVm.builder().descrizione(request.getDescription(false)).message(ex.getMessage())
				.statusCode(HttpStatus.BAD_REQUEST.value()).build();

	}
	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ErrorVm handleGlobalExecptionArgument(Exception ex, WebRequest request) {
		log.error(GENERIC_EXCEPTION + ex.getMessage(), ex);
		return ErrorVm.builder().descrizione(request.getDescription(false)).message(ex.getMessage())
				.statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value()).build();

	}

}
