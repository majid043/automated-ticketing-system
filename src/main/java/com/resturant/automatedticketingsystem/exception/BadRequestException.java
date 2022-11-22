package com.resturant.automatedticketingsystem.exception;

public class BadRequestException extends RuntimeException {
	private static final long serialVersionUID = -3500172637210937549L;

	public BadRequestException(String exception) {
		super(exception);
	}
}
