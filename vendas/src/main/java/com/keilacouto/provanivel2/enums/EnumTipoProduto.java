package com.keilacouto.provanivel2.enums;

public enum EnumTipoProduto {

	PRODUTO("Produto"),
    SERVICO("Serviço");

    private String descricao;

    EnumTipoProduto(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
