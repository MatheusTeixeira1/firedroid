package br.com.firedroid.exception;

public class DuplicateIdentifierException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	public DuplicateIdentifierException() {
		super("Identificador duplicado");
	}
	public DuplicateIdentifierException(String message) {
		super(message);
	}
}

