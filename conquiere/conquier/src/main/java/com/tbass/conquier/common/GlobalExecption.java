package com.tbass.conquier.common;

import java.time.LocalDateTime;

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
	private static String NO_RESOURCE_FOUND_EXCEPTION = "AUCUNE RESSOURCE TROUVE - ";
	private static String GENERIC_EXCEPTION = "ERREUR GENERICQUE - ";

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ErrorVm handleInvalidArgument(MethodArgumentNotValidException ex, WebRequest request) {
		return getErrorMessage(HttpStatus.BAD_REQUEST, ex, request, NOT_VALID_EXCEPTION);

	}

	@ExceptionHandler(NoResourceFoundException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ErrorVm handleNoResourceFoundException(NoResourceFoundException ex, WebRequest request) {
		return getErrorMessage(HttpStatus.NOT_FOUND, ex, request, NO_RESOURCE_FOUND_EXCEPTION);

	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ErrorVm handleGlobalExecptionArgument(Exception ex, WebRequest request) {
		return getErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, ex, request, GENERIC_EXCEPTION);
	}

	private ErrorVm getErrorMessage(HttpStatus httpStatus, Exception ex, WebRequest request, String errorMessage) {
		log.error(errorMessage + ex.getMessage(), ex);
		return ErrorVm.builder().descrizione(request.getDescription(false)).message(ex.getMessage())
				.statusCode(httpStatus.value()).timestamp(LocalDateTime.now()).build();
	}

}
