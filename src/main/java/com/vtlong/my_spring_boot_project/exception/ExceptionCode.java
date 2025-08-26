package com.vtlong.my_spring_boot_project.exception;

public enum ExceptionCode {
    USER_NOT_FOUND("USER_001", "User not found"),
    USER_ALREADY_EXISTS("USER_002", "User already exists"),
    EMAIL_ALREADY_EXISTS("USER_003", "Email already exists"),
    INVALID_USER_DATA("USER_005", "Invalid user data"),
    
    UNAUTHORIZED("AUTH_001", "Unauthorized access"),
    FORBIDDEN("AUTH_002", "Access forbidden"),
    INVALID_CREDENTIALS("AUTH_003", "Invalid credentials"),
    TOKEN_EXPIRED("AUTH_004", "Token expired"),
    INVALID_TOKEN("AUTH_005", "Invalid token"),
    
    VALIDATION_ERROR("VAL_001", "Validation error"),
    REQUIRED_FIELD_MISSING("VAL_002", "Required field missing"),
    INVALID_FORMAT("VAL_003", "Invalid format"),
    
    DATABASE_ERROR("DB_001", "Database error"),
    CONSTRAINT_VIOLATION("DB_002", "Constraint violation"),
    
    INTERNAL_SERVER_ERROR("GEN_001", "Internal server error"),
    RESOURCE_NOT_FOUND("GEN_002", "Resource not found"),
    BAD_REQUEST("GEN_003", "Bad request"),
    METHOD_NOT_ALLOWED("GEN_004", "Method not allowed");

    private final String code;
    private final String defaultMessage;

    ExceptionCode(String code, String defaultMessage) {
        this.code = code;
        this.defaultMessage = defaultMessage;
    }

    public String getCode() {
        return code;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }
}
