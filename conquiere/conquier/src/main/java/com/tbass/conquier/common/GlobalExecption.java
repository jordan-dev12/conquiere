package com.tbass.conquier.common;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExecption {

	private static String NOT_VALID_EXCEPTION = "ERREUR DE VALIDATION ";
	private static String NO_RESOURCE_FOUND_EXCEPTION = "AUCUNE RESSOURCE TROUVE ";
	private static String GENERIC_EXCEPTION = "ERREUR GENERICQUE ";

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ProblemDetail handleInvalidArgument(MethodArgumentNotValidException ex, WebRequest request) {
		return getErrorMessage(HttpStatus.BAD_REQUEST, ex, request, NOT_VALID_EXCEPTION);

	}

	@ExceptionHandler(NoResourceFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public ProblemDetail handleNoResourceFoundException(NoResourceFoundException ex, WebRequest request) {
		return getErrorMessage(HttpStatus.NOT_FOUND, ex, request, NO_RESOURCE_FOUND_EXCEPTION);

	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public ProblemDetail handleGlobalExecptionArgument(Exception ex, WebRequest request) {
		return getErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, ex, request, GENERIC_EXCEPTION);
	}

	private ProblemDetail getErrorMessage(HttpStatus httpStatus, Exception ex, WebRequest request,
			String errorMessage) {
		log.error(errorMessage + ex.getMessage(), ex);

		ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(httpStatus, ex.getMessage());
		problemDetail.setTitle(errorMessage);
		problemDetail.setProperty("timestamp", Instant.now());

		return problemDetail;

	}

}
