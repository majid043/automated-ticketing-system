package com.resturant.automatedticketingsystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) 
public class InternalServerException extends RuntimeException {
	private static final long serialVersionUID = 7601645530303719325L;

	public InternalServerException(String message, Throwable cause) {
		super(message, cause);
	}
}
