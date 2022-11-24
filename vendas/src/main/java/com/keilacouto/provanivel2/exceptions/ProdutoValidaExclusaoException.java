package com.keilacouto.provanivel2.exceptions;

public class ProdutoValidaExclusaoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ProdutoValidaExclusaoException() {
        super("Não é possível excluir o produto, pois o mesmo está associado a algum pedido.");
    }

}