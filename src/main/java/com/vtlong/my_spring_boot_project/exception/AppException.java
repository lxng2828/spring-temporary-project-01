package com.vtlong.my_spring_boot_project.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class AppException extends RuntimeException {
    private final HttpStatus status;
    private final ExceptionCode code;
    private final String message;

    public AppException(HttpStatus status, String message, ExceptionCode code) {
        super(message);
        this.message = message;
        this.status = status;
        this.code = code;
    }

    public AppException(HttpStatus status, ExceptionCode code) {
        super(code.getDefaultMessage());
        this.message = code.getDefaultMessage();
        this.status = status;
        this.code = code;
    }

    public AppException(ExceptionCode code) {
        super(code.getDefaultMessage());
        this.message = code.getDefaultMessage();
        this.status = HttpStatus.BAD_REQUEST;
        this.code = code;
    }

    public AppException(String message, ExceptionCode code) {
        super(message);
        this.message = message;
        this.status = HttpStatus.BAD_REQUEST;
        this.code = code;
    }

    public AppException(HttpStatus status, String message, ExceptionCode code, Throwable cause) {
        super(message, cause);
        this.message = message;
        this.status = status;
        this.code = code;
    }

    public AppException(ExceptionCode code, Throwable cause) {
        super(code.getDefaultMessage(), cause);
        this.message = code.getDefaultMessage();
        this.status = HttpStatus.BAD_REQUEST;
        this.code = code;
    }
}
