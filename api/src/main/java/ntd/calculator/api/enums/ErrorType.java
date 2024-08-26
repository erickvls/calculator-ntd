package ntd.calculator.api.enums;

public enum ErrorType {
    VALIDATION_ERROR ("Validation error"),
    AUTHENTICATION_ERROR("Authentication error"),
    INTERNAL_SERVER_ERROR("Internal server error");

    private final String type;

    ErrorType(String type) {
        this.type = type;
    }

    public String getType(){
        return type;
    }
}
