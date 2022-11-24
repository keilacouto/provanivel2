package com.keilacouto.provanivel2.exceptions;

public class ProdutoNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ProdutoNotFoundException(String exception) {
        super(exception);
    }

}