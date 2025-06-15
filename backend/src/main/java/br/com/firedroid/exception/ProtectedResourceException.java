package br.com.firedroid.exception;

public class ProtectedResourceException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    
    private String action;
    private String entityName;
    private String reason;
    private static final String MESSAGE_MODEL = "Não pode %s %s pois %s"; // Agora é `static`
    
    public ProtectedResourceException() {
        super("Este é um recurso protegido pelo servidor");
    }
    
    public ProtectedResourceException(String message) {
        super(message);
    }
    
    public ProtectedResourceException(String action, String entityName, String reason) {
        super(String.format(MESSAGE_MODEL, action, entityName, reason)); // Usa a constante `static`
        this.action = action;
        this.entityName = entityName;
        this.reason = reason;
    }
    
    // Getters (opcional)
    public String getAction() { return action; }
    public String getEntityName() { return entityName; }
    public String getReason() { return reason; }
}