package com.keilacouto.provanivel2.exceptions;

public class PedidoNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public PedidoNotFoundException(String exception) {
        super(exception);
    }

}