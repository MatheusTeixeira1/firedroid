package br.com.firedroid.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(DuplicateDisplayOrderException.class)
	private ResponseEntity<ErrorMessage> duplicateDisplayOrderHandler(DuplicateDisplayOrderException e) {
		ErrorMessage response = new ErrorMessage(HttpStatus.CONFLICT, e.getMessage());
		return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
	}

	@ExceptionHandler(CustomEntityNotFoundException.class)
	private ResponseEntity<ErrorMessage> gameNotFoundHandler(CustomEntityNotFoundException e) {
		ErrorMessage response = new ErrorMessage(HttpStatus.NOT_FOUND, e.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	}

	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		List<String> errors = new ArrayList<>();

		ex.getBindingResult().getFieldErrors().forEach(error -> errors.add(error.getDefaultMessage()));

		ValidationErrorMessage response = new ValidationErrorMessage(HttpStatus.BAD_REQUEST,
				"Erro de validação nos campos", errors);

		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ProtectedResourceException.class)
	public ResponseEntity<ErrorMessage> protectedResourceHandler(ProtectedResourceException e) {
		ErrorMessage error = new ErrorMessage(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(error);
	}

	@ExceptionHandler(InvalidUsernameException.class)
	private ResponseEntity<ErrorMessage> handleInvalidUsername(InvalidUsernameException e) {
		ErrorMessage error = new ErrorMessage(HttpStatus.UNAUTHORIZED, e.getMessage());
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
	}

	@ExceptionHandler(DuplicateIdentifierException.class)
	private ResponseEntity<ErrorMessage> handleDuplicateIdentifier(DuplicateIdentifierException e) {
		ErrorMessage error = new ErrorMessage(HttpStatus.CONFLICT, e.getMessage());
		return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
	}


}
