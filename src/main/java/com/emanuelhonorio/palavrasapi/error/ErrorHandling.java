package com.emanuelhonorio.palavrasapi.error;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.emanuelhonorio.palavrasapi.error.exception.EntityNotValidException;

@ControllerAdvice
public class ErrorHandling extends ResponseEntityExceptionHandler {

	@Autowired
	private MessageSource messageSource;
	
	@ExceptionHandler(EntityNotValidException.class)
	public ResponseEntity<?> handleEntityNotValidException(EntityNotValidException ex) {
		String userMessage = ex.getMessage();
		String devMessage = ExceptionUtils.getRootCauseMessage(ex);
		return buildResponseEntity(new Error(userMessage, devMessage), HttpStatus.BAD_REQUEST);
	}
	
	/**
	 * Handle resource not found
	 * 
	 * @param ex javax.persistence.EntityNotFoundException or EmptyResultDataAccessException
	 * @return
	 */
	@ExceptionHandler({EntityNotFoundException.class, EmptyResultDataAccessException.class})
	public ResponseEntity<?> handleEntityNotFoundException(Exception ex) {
		String userMessage = messageSource.getMessage("error.generic-not-found", null, LocaleContextHolder.getLocale());
		String devMessage = ExceptionUtils.getRootCauseMessage(ex);
		return buildResponseEntity(new Error(userMessage, devMessage), HttpStatus.NOT_FOUND);
	}
	
	
	
	/**
	 * Handles javax.validation.ConstraintViolationException. Thrown when @Validated fails.
	 * 
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(javax.validation.ConstraintViolationException.class)
	public ResponseEntity<?> handleConstraintViolationException(
			javax.validation.ConstraintViolationException ex) {
		String userMessage = messageSource.getMessage("error.generic-bad-request", null, LocaleContextHolder.getLocale());
		String devMessage = ExceptionUtils.getRootCauseMessage(ex);
		return buildResponseEntity(new Error(userMessage, devMessage), HttpStatus.BAD_REQUEST);
	}
	
	/**
	 * Handle DataIntegrityViolationException, inspects the cause for different DB causes.
	 * 
	 * @param ex DataIntegrityViolationException
	 * @return Error Object
	 */
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
		String userMessage = messageSource.getMessage("error.database", null, LocaleContextHolder.getLocale());
		String devMessage = ExceptionUtils.getRootCauseMessage(ex);
		return buildResponseEntity(new Error(userMessage, devMessage), HttpStatus.BAD_REQUEST); //TODO: talvez mudar O HttpStatus
	}
	
	@ExceptionHandler(IllegalStateException.class)
	public ResponseEntity<Object> handleIllegalStateException(IllegalStateException ex) {
		String userMessage = messageSource.getMessage("error.generic-bad-request", null, LocaleContextHolder.getLocale());
		String devMessage = ExceptionUtils.getRootCauseMessage(ex);
		return buildResponseEntity(new Error(userMessage, devMessage), HttpStatus.BAD_REQUEST);
	}

	/**
	 * Handle @Valid method parameters
	 */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<Error> errorList = criarListaDeErroDosCampos(ex.getBindingResult());
		return buildResponseEntity(errorList, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Handle Generic ResponseEntity exceptions
	 */
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		String userMessage = messageSource.getMessage("error.generic-bad-request", null, LocaleContextHolder.getLocale());
		String devMessage = ExceptionUtils.getRootCauseMessage(ex);
		return buildResponseEntity(new Error(userMessage, devMessage), HttpStatus.BAD_REQUEST);
	}

	private List<Error> criarListaDeErroDosCampos(BindingResult bindingResult) {
		List<Error> errorList = new ArrayList<>();
		for (FieldError fieldError : bindingResult.getFieldErrors()) {
			String userMessage = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
			String devMessage = fieldError.getCode();
			errorList.add(new Error(userMessage, devMessage));
		}
		return errorList;
	}
	
	private ResponseEntity<Object> buildResponseEntity(Error apiError, HttpStatus httpStatus) {
		return new ResponseEntity<>(Arrays.asList(apiError), httpStatus);
	}
	
	private ResponseEntity<Object> buildResponseEntity(List<Error> apiError, HttpStatus httpStatus) {
		return new ResponseEntity<>(apiError, httpStatus);
	}
}
