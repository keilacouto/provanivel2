package com.keilacouto.provanivel2.exceptions;

public class ItemPedidoNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ItemPedidoNotFoundException(String exception) {
        super(exception);
    }

}