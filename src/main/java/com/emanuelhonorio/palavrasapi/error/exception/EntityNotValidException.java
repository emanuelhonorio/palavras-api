package com.emanuelhonorio.palavrasapi.error.exception;

public class EntityNotValidException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public EntityNotValidException(String mensagem) {
		super(mensagem);
	}
}
