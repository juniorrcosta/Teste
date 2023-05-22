package br.com.santander.internetbanking.config;

import org.springframework.http.HttpStatus;

public class ResourceBusinessException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private HttpStatus httpStatus;
    private String errorCode;

    public ResourceBusinessException(HttpStatus httpStatus, String errorCode, String message) {
        super(message);
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getErrorCode() {
        return errorCode;
    }
}