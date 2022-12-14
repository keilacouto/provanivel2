package com.keilacouto.provanivel2.model;

import java.math.BigDecimal;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.keilacouto.provanivel2.enums.EnumStatusProduto;
import com.keilacouto.provanivel2.enums.EnumTipoProduto;

//import lombok.Builder;

//@Builder
@Entity
@Table(name = "produto")
public class Produto {

	@Id
    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
	private UUID id;

	@Column(name = "descricao")
	private String descricao;

	@Column(name = "valor_unitario")
	private BigDecimal valorUnitario;

	@Enumerated(EnumType.STRING)
	@Column(name = "tipo")
	private EnumTipoProduto tipo;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private EnumStatusProduto status;
	
	public Produto() {

	}
	
	public Produto(UUID id, String descricao, BigDecimal valorUnitario, EnumTipoProduto tipo,
			EnumStatusProduto status) {
		super();
		this.id = id;
		this.descricao = descricao;
		this.valorUnitario = valorUnitario;
		this.tipo = tipo;
		this.status = status;
	}



	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public BigDecimal getValorUnitario() {
		return valorUnitario;
	}

	public void setValorUnitario(BigDecimal valorUnitario) {
		this.valorUnitario = valorUnitario;
	}

	public EnumTipoProduto getTipo() {
		return tipo;
	}

	public void setTipo(EnumTipoProduto tipo) {
		this.tipo = tipo;
	}

	public EnumStatusProduto getStatus() {
		return status;
	}

	public void setStatus(EnumStatusProduto status) {
		this.status = status;
	}
	
	@Override
	public String toString() {
		return "Produto [id=" + id + ", descricao=" + descricao + ", valorUnitario=" + valorUnitario + ", tipo=" + tipo
				+ ", status=" + status + "]";
	}
}
