package com.keilacouto.provanivel2.exceptions;

public class EmployeeNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EmployeeNotFoundException(String exception) {
        super(exception);
    }

}