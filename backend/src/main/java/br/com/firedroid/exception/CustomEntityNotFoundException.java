package br.com.firedroid.exception;

import jakarta.persistence.EntityNotFoundException;

public class CustomEntityNotFoundException extends EntityNotFoundException {
	private static final long serialVersionUID = 1L;
	
    private final String entityName;
    private final String stringFieldName;
    private final String stringIdentifier;
    private final Number numericIdentifier;

 // Construtor direto com mensagem personalizada
    public CustomEntityNotFoundException(String message) {
        super(message);
        this.entityName = null;
        this.stringFieldName = null;
        this.stringIdentifier = null;
        this.numericIdentifier = null;
    }

    // Construtor para identificador numérico
    public CustomEntityNotFoundException(String entityName, Number numericIdentifier) {
        super(String.format("%s com id %s não encontrado", entityName, numericIdentifier));
        this.entityName = entityName;
        this.numericIdentifier = numericIdentifier;
        this.stringFieldName = null;
        this.stringIdentifier = null;
    }

    // Construtor para identificador string
    public CustomEntityNotFoundException(String entityName, String stringFieldName, String stringIdentifier) {
        super(String.format("%s com %s '%s' não encontrado(a)", entityName, stringFieldName, stringIdentifier));
        this.entityName = entityName;
        this.stringFieldName = stringFieldName;
        this.stringIdentifier = stringIdentifier;
        this.numericIdentifier = null;
    }

    public String getEntityName() {
        return entityName;
    }

    public String getStringFieldName() {
        return stringFieldName;
    }

    public String getStringIdentifier() {
        return stringIdentifier;
    }

    public Number getNumericIdentifier() {
        return numericIdentifier;
    }
}
