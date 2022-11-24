package com.keilacouto.provanivel2.enums;

public enum EnumStatusPedido {

	ABERTO("Aberto"),
    FECHADO("Fechado");

    private String descricao;

    EnumStatusPedido(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
