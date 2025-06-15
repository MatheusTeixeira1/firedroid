package br.com.firedroid.exception;

public class DuplicateDisplayOrderException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	public DuplicateDisplayOrderException() {
		super("Esta posição de exibição já está em uso");
	}
	public DuplicateDisplayOrderException(String message) {
		super(message);
	}
}
