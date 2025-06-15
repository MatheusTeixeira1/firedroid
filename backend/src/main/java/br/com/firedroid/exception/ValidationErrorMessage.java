package br.com.firedroid.exception;

import java.util.List;

import org.springframework.http.HttpStatus;

public class ValidationErrorMessage {
    private HttpStatus status;
    private String message;
    private List<String> errors;

    public ValidationErrorMessage(HttpStatus status, String message, List<String> errors) {
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<String> getErrors() {
        return errors;
    }


}
