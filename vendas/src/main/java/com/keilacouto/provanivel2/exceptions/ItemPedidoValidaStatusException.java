package com.keilacouto.provanivel2.exceptions;

public class ItemPedidoValidaStatusException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ItemPedidoValidaStatusException() {
        super("Não é possível adicionar o produto, pois o mesmo encontra-se com status Inativo.");
    }

}