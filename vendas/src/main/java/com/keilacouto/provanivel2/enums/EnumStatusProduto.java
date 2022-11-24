package com.keilacouto.provanivel2.enums;

public enum EnumStatusProduto {

	ATIVO("Ativo"),
    INATIVO("Inativo");

    private String descricao;

    EnumStatusProduto(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
